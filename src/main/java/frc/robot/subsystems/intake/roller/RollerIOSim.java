package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Constants.IntakeConstants;

public class RollerIOSim implements RollerIO {
  private DCMotorSim m_sim;
  private double m_voltage;

  public RollerIOSim() {
    m_sim =
        new DCMotorSim(
            IntakeConstants.kSimGearing, IntakeConstants.kSimGearbox, IntakeConstants.kSimMOI);
    m_voltage = 0;
  }

  @Override
  public void updateInputs(RollerInputs inputs) {
    m_sim.update(0.02);
    m_sim.setInputVoltage(m_voltage);

    inputs.current = m_sim.getCurrentDrawAmps();
    inputs.velocity = m_sim.getAngularVelocityRPM();
    inputs.voltage = m_voltage;
  }

  @Override
  public void setVoltage(double voltage) {
    m_voltage = voltage;
  }
}
