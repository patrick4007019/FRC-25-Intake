package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.intake.roller.RollerInputsAutoLogged;
import frc.robot.util.SubsystemProfiles;
import java.util.HashMap;
import org.littletonrobotics.junction.Logger;

public class Intake extends SubsystemBase {
  private RollerIO m_rollerIO;
  public final RollerInputsAutoLogged m_rollerInputs;
  private SubsystemProfiles m_profiles;

  public enum RollerState {
    kIdle,
    KIntaking
  }

  public Intake(IntakeIO io) {
    m_io = io;
    m_rollerInputs = new RollerInputsAutoLogged();

    HashMap<Enum<?>, Runnable> periodicHash = new HashMap();
    periodicHash.put(RollerState.kIdle, this::idlePeriodic);
    periodicHash.put(RollerState.kIntaking, this::intakingPeriodic);
    m_profiles = new SubsystemProfiles<>(periodicHash, RollerState.kIdle);
  }

  @Override
  public void periodic() {
    double begin = Timer.getFGATimestamp();
    m_rollerIO.updateInputs(m_rollerInputs);
    m_profiles.getPeriodicFunction().run();

    Logger.processInputs("Roller", m_rollerInputs);
    Logger.recordOutputs("Intake State", m_profiles.getCurrentState());
    Logger.recordOutputs("Periodic Time", Timer.getFGATimestamp() - begin);
  }

  public void idlePeriodic() {
    m_io.setVoltage(IntakeConstants.kIdleVoltage);
  }

  public void intakingPeriodic() {
    m_io.setVoltage(IntakeConstants.kIntakingVoltage);
  }

  public void updateState(IntakeState state) {
    switch (state) {
      case kIdle:
        m_io.setVoltage(IntakeConstants.kIdleVoltage);
        break;
      case kIntaking:
        m_io.setVoltage(IntakeConstants.kIntakingVoltage);
        break;
    }
    m_profiles.setCurrentProfile(state);
  }

  public IntakeState getIntakeState() {
    return m_profiles.getCurrentProfile();
  }
}
