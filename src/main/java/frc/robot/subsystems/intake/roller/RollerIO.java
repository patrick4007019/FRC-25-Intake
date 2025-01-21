package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface RollerIO {
  @AutoLog
  public static class RollerInputs {
    public double velocity;
    public double current;
    public double voltage;
  }

  public void updateInputs(RollerInputs inputs);

  public void setVoltage(double voltage);
}
