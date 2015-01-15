package net.aisd.martin.frc2013;

import net.aisd.martin.frc.imageprocessing.ImageProcessing;

/**
 * This class will allow us to store "Subsystems" which are segments of our
 * robot These are similar to the built in subsystems but are coded and designed
 * by us making the code simpler, cleaner, and more efficient. Specifically it
 * allows subsystems to be used in many different situations, but with this
 * power comes great responsablity. DON'T TELL A SUBSYSTEM TO DO 2 THINGS AT
 * ONCE.
 *
 * @author Neil
 */
//For more info compare the subsystem class provided by FRC with ours
public class Subsystems {
	//Variables that holds all of our subsystems
	//None beyond the basics because we don't have a robot

	public static Pneumatics pneumatics;
	public static DriveTrain driveTrain;
	public static Controller controller1;
	public static Controller controller2;
	public static Shooter shooter;
	public static PClimber climber;
	public static Cameras camera;
	public static SmartDash smartDash;
	public static ImageProcessing IP;
	/*
	 * This instantiates all the robots subsystems. Will probably be called in the
	 * robot main's constuctor
	 */

	public static void init() {
		pneumatics = new Pneumatics();
		controller1 = new Controller(RobotMap.Input.joystick1);
		controller2 = new Controller(RobotMap.Input.joystick2);
		driveTrain = new DriveTrain(RobotMap.DriveMotors.slot,
				RobotMap.DriveMotors.left_front,
				RobotMap.DriveMotors.right_front);

		shooter = new Shooter(RobotMap.Shooter.motor_slot, RobotMap.Shooter.front, RobotMap.Shooter.back,
				RobotMap.Shooter.pnuematics_slot, RobotMap.Shooter.piston_forward, RobotMap.Shooter.piston_backwards, RobotMap.Shooter.ejection_piston);
		climber = new PClimber(RobotMap.PClimber.pnuematics_slot, RobotMap.PClimber.cpiston_forward, RobotMap.PClimber.cpiston_backwards);
		camera = new Cameras(RobotMap.Camera.slot, RobotMap.Camera.Yaxis, RobotMap.Camera.Xaxis, RobotMap.Camera.light);
		smartDash = new SmartDash();
		IP = new ImageProcessing();
	}
}
