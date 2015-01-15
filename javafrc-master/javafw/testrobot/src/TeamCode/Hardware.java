package TeamCode;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalModule;

/**
 * A class that contains static fields representing hardware. Call init once to initialize
 * all needed hardware.
 */
public class Hardware 
{
    /** Left drive motor managed by the TankDrive Command. */
    public static Talon driveMotorLeft;
    /** Right drive motor managed by the TankDrive Command. */
    public static Talon driveMotorRight;
    
    /**
     * Initialize all hardware for use by Commands. Should be called once in robotInit.
     */
    public static void init()
    {
        driveMotorLeft = new Talon(1, 1); // digital module 1, PWM channel 1
        driveMotorRight = new Talon(1, 2); //digital module 1, PWM channel 2
    }
}
