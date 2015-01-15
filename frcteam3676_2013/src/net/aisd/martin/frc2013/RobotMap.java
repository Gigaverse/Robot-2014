package net.aisd.martin.frc2013;

/**
 *
 * @author Neil
 *
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public final class RobotMap {

	/**
	 * Signals a value that needs changed Used in code as a placeholder for
	 * hardware that hasn't been assigned a slot/channel yet to find usages that
	 * need to be replaced in netbeans right click the variable and click "Find
	 * Usage"
	 */
	public static final int INVALID = Integer.MIN_VALUE;
	/**
	 * Signals a value that might need to be removed Used when the hardware team
	 * can't make up their freakin' minds and continuously remove and add a
	 * device
	 */
	public static final int UNUSED = Integer.MIN_VALUE;

	/**
	 * Contains input device mappings (joysticks and gamepads
	 */
	public static final class Input {

		private Input() {
		}
		public static final int joystick1 = 1;
		public static final int joystick2 = 2;
	}

	/*
	 * Contains all the nessacery slots and channels for the drive train
	 */
	public static final class DriveMotors {

		private DriveMotors() {
		}
		//Slot the motors are located in
		public static final int slot = 1;
		//Use front left and right if only two motors are used
		public static final int left_front = 1;
		public static final int right_front = 2;
		//Use these motors if we use more than two
		public static final int left_back = UNUSED;
		public static final int right_back = UNUSED;
	}

	/*
	 * Contains the slot and channel of all the compressor pnuematic hardware
	 */
	public static final class Shooter {

		private Shooter() {
		}
		public static final int pnuematics_slot = 1;
		public static final int motor_slot = 1;
		public static final int piston_forward = 1;
		public static final int piston_backwards = 2;
                public static final int ejection_piston = 5;
		public static final int front = 5;
		public static final int back = 6;
	}

	public static final class Pneumatics {

		private Pneumatics() {
		}
		//Slot that pnumatics are located in
		public static final int slot = 1;
		//Channels
		public static final int compressor = 1;
		public static final int pressure_switch = 1;
	}

	public static final class PClimber {

		private PClimber() {
		}
		public static final int pnuematics_slot = 1;
		public static final int cpiston_forward = 3;
		public static final int cpiston_backwards = 4;
	}

	public static final class Camera {

		public static final String IPaddressTarget = "10.36.76.11";
		public static final String IPaddressRotate = "10.36.76.12";
		public static final int slot = 1;
		public static final int Yaxis = 7;
		public static final int Xaxis = 8;
		public static final int light = 2;
	}
}
