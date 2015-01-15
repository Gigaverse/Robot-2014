package net.aisd.martin.frc2013;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This system contains and relays all the data of one controller of the fake
 * X-BOX type
 *
 * @author Neil
 */
class Controller {

	private Joystick joystick;
	//This is for numbers we don't know yet!
	private final static int number = 2;

	public Controller(int channel) {
		this.joystick = new Joystick(channel);
	}

	/**
	 * The rest of the methods are used basically wrappers that allow us to
	 * better understand what each button is EX(I am making something up):
	 * Instead of button3 we will have a getLeftBumper method that will return
	 * button3's value or if the controller is null 0 or false depending on the
	 * button
	 */
	/**
	 * These methods return either their raw value or a squared value which is
	 * useful in some cases
	 *
	 * The left and right axis are swapped, but using the controller still works
	 * with perfect control, so I wouldn't mess with them
	 */
	public double getRightXAxis() {
		return joystick.getRawAxis(4);
	}

	public double getRightYAxis() {
		return joystick.getRawAxis(5);
	}

	public double getLeftXAxis() {
		return joystick.getRawAxis(1);
	}

	public double getLeftYAxis() {
		return joystick.getRawAxis(2);
	}

	public double getTriggers() {
		return joystick.getRawAxis(3);
	}

	//Natively the joystick returns this as a double
	//converts to a boolean
	public boolean getDPadLeft() {
		if (joystick.getRawAxis(6) == -1) {
			return true;
		}
		return false;
	}

	public boolean getDPadRight() {
		if (joystick.getRawAxis(6) == 1) {
			return true;
		}
		return false;
	}

	public boolean getDPadUp() {
		if (joystick.getRawAxis(7) == 1) {
			return true;
		}
		return false;
	}

	public boolean getDPadDown() {
		if (joystick.getRawAxis(7) == -1) {
			return true;
		}
		return false;
	}

	/**
	 * These methods do not return a squared value since they cannot do this
	 * Helpful hint did some experimenting and multiplying a boolean by itself
	 * actually does a "bitwise operation". Completely useless for us
	 */
	public boolean getLeftAxisButton() {
		return joystick.getRawButton(9);
	}

	public boolean getRightAxisButton() {
		return joystick.getRawButton(10);
	}

	/**
	 * TODO: Find out which numbers go where TODO: Put the triggers once we find
	 * out if they return doubles or floats
	 */
	public boolean getAButton() {
		return joystick.getRawButton(1);
	}

	public boolean getBButton() {
		return joystick.getRawButton(2);
	}

	public boolean getYButton() {
		return joystick.getRawButton(4);
	}

	public boolean getXButton() {
		return joystick.getRawButton(3);
	}

	public boolean getRightBumper() {
		return joystick.getRawButton(6);
	}

	public boolean getLeftBumper() {
		return joystick.getRawButton(5);
	}

	public boolean getStartButton() {
		return joystick.getRawButton(8);
	}

	public boolean getSelectButton() {
		return joystick.getRawButton(7);
	}
}
