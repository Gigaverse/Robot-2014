package TeamCode;

/**
 * This class contains static fields representing useful values obtained from sensors or gamepads.
 * Code that wants to interact with global variables can read from and write to them directly.
 * Call init once to initialize these variables to logical values.
 */
public class GlobalData 
{
    /** The input value for the left side motors. */
    public static double cTankDriveLeft;
    /** The input value for the right side motors. */
    public static double cTankDriveRight;
    
    /**
     * Initialize the global variables to logical values. Should be called once in robotInit.
     */
    public static void init()
    {
        cTankDriveLeft = 0;
        cTankDriveRight = 0;
    }
}
