package TeamCode;

import ControlLoops.TankDrive;
import edu.wpi.first.wpilibj.command.Command;
import java.util.Vector;

/**
 * A class containing static fields that represent control loops. Calling init will instantiate
 * these Commands, which only needs to be done once. Calling start will add them to the 
 * Scheduler.
 */
public class ControlLoops 
{
    /** A list of commands to make life easier at some point. */
    public static Vector commands;  
    /** Tank drive (what??). */
    public static TankDrive tankDrive;
    
    /**
     * Instantiate all needed commands and add them to the master list. Should be called once
     * in robotInit.
     */
    public static void init()
    {
        commands = new Vector();
        
        tankDrive = new TankDrive();
        commands.addElement(tankDrive);
    }
    
    /**
     * Add all the Commands to the Scheduler. Afaik must be called every time the robot enters a 
     * new mode (but this shouldn't affect any data that the Commands were storing).
     */
    public static void start()
    {
        for (int i = 0; i < commands.size(); i++)
        {
            ((Command) commands.elementAt(i)).start();
        }
    }
}
