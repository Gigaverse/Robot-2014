package TeamCode;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * A main class for the Java Robot. 
 */
public class Main extends IterativeRobot
{
    /**
     * Initialize robot functions on power-up.
     */
    public void robotInit()
    {
        Hardware.init();
        GlobalData.init();
        ControlLoops.init();
    }
    
    /**
     * Make sure all the Commands are added to the Scheduler when autonomous starts.
     */
    public void autonomousInit()
    {
        ControlLoops.start();
    }
    
    /**
     * Run appropriate autonomous code.
     */
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
    }
    
    /**
     * Make sure all the Commands are added to the Scheduler when teleop starts.
     */
    public void teleopInit()
    {
        ControlLoops.start();
    }
    
    /**
     * Run appropriate teleop code.
     */
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();
    }
    
    /**
     * Code that runs when the robot enters disabled mode (intentionally empty).
     */
    public void disabledInit() {}
    
    /**
     * Code that is called periodically in disabled mode (intentionally empty).
     */
    public void disabledPeriodic() {}
    
    /**
     * Code that runs when the robot enters test mode (intentionally empty).
     */
    public void testInit() {}
    
    /**
     * Code that is called periodically in test mode (intentionally empty).
     */
    public void testPeriodic() {}
}
