package net.aisd.martin.frc2013;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * This class controls all the systems related to moving the robot. This
 * includes ALL mobility devices from arms, to wheels, to drive motors, to a jet
 * rocket.
 *
 * Right now we just have a two wheel drive but it is easy to change this. Just
 * add or remove any controllers you need and update tick accordingly.
 *
 * WE MAY NEED TO INVERSE MOTORS CONTROL, DEPENDING ON HOW THE TEAM WIRES THEM,
 * RUN WITH EXTREME CAUTION AND DO NOT BURN OUT THE MOTORS!!!!
 *
 * @author Neil
 */
public class DriveTrain {
	//These are our speed controllers. Will change what they equal when we need to move

	private Victor frontLeft;
	private Victor frontRight;

	/**
	 * Creates a two wheel drive train
	 *
	 * @param slot the slot for the motors
	 * @param left the left motor's channel
	 * @param right the right motor's channel
	 * @param controller the controller class that will control the drivetrain
	 *
	 * This class and its constructor will be updated frequently! Please label
	 * what you change and why!!!
	 */
	public DriveTrain(int slot, int frontLeft, int frontRight) {
		this.frontLeft = new Victor(slot, frontLeft);
		this.frontRight = new Victor(slot, frontRight);
	}

	/*
	 * Anything in this class that needs to be updated each by the main thread 
	 * will go in this method. Will be called in the Team3676Robot operator control
	 * loop
	 * @param none
	 */
	public void tick(double forward, double rotation, boolean precision) {
		/**
		 * This one gets more complex we need to find the "Rotation value" and
		 * also the speed value and set them accordingly. Will take fine tuning
		 * Borrowed this code from FRC. We don't need to customize it yet or
		 * hopefully ever cause I'm pretty sure it works with black magic
		 */
		double right = forward + rotation;
		double left = forward - rotation;
		left = Math.max(left, -1);
		right = Math.max(right, -1);
		left = Math.min(left, 1);
		right = Math.min(right, 1);
		if (precision) {
			left *= .5;
			right *= .5;
		}
		frontLeft.set(left);
		frontRight.set(right);
	}

	public void autoTick(double[] position) {
		//First aligns the x axis then aligns distance
		if (position[1] > .5) {
			frontRight.set(-.5);
			NetworkTable.getTable("SmartDash").putBoolean("X-axis", false);
		} else if (position[1] > .3) {
			frontRight.set(-.2);
			NetworkTable.getTable("SmartDash").putBoolean("X-axis", false);
		} else if (position[1] > .1) {
			frontRight.set(-.1);
			NetworkTable.getTable("SmartDash").putBoolean("X-axis", false);
		} else if (position[1] > -.1) {
			frontRight.set(.0);
			NetworkTable.getTable("SmartDash").putBoolean("X-axis", true);
		} else if (position[1] > -.3) {
			frontRight.set(.1);
			NetworkTable.getTable("SmartDash").putBoolean("X-axis", false);
		} else if (position[1] > -.5) {
			frontRight.set(.2);
			NetworkTable.getTable("SmartDash").putBoolean("X-axis", false);
		} else if (position[1] <= -.5) {
			frontRight.set(.5);
			NetworkTable.getTable("SmartDash").putBoolean("X-axis", false);
		}

	}
}