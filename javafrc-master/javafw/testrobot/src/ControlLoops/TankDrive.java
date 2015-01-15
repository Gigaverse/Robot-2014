package ControlLoops;

import TeamCode.GlobalData;
import TeamCode.Hardware;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A Command for the right and left drive motors.
 */
public class TankDrive extends Command
{
    /**
     * Nothing to do in initialize (taken care of in the Commands class).
     */
    public void initialize() {}
    
    /**
     * Set the motors to the values obtained from the GlobalData.
     */
    public void execute()
    {
        Hardware.driveMotorLeft.set(GlobalData.cTankDriveLeft);
        Hardware.driveMotorRight.set(GlobalData.cTankDriveRight);
    }
    
    /**
     * Determine whether or not this Command should stop (which is never).
     * @return false because this should run forever
     */
    public boolean isFinished()
    {
        return false;
    }
    
    /**
     * Shut off the motors when this Command stops.
     */
    public void end()
    {
        Hardware.driveMotorLeft.set(0);
        Hardware.driveMotorRight.set(0);
    }
    
    /**
     * Shut off the motors if this Command is interrupted.
     */
    public void interrupted()
    {
        end();
    }
}
