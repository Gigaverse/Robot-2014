/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aisd.martin.frc2013;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import net.aisd.martin.frc2013.Subsystems;

/**
 *
 * @author Neil
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team3676Robot extends SimpleRobot {

	/**
	 * Robot's instance variables
	 */
	/**
	 * This is the robot's initialization Instantiates any objects that will be
	 * used by the robot here
	 */
	private double readyTime;
	private int counter;
	private double turnTime;
	private double forwardTime;
	private double endTime;

	public void robotInit() {
		Subsystems.init();
		Subsystems.pneumatics.compressor.start();
		Watchdog.getInstance().setExpiration(.5);
	}

	/*
	 * This function is called when the robot is disabled
	 */
	public void disabled() {
	}

	/**
	 * This function is called once each time the robot enters autonomous mode.
	 */
	public void autonomous() {
		readyTime = System.currentTimeMillis() + 3000;
		turnTime = System.currentTimeMillis() + 8000;
		forwardTime = System.currentTimeMillis() + 9000;
		endTime = System.currentTimeMillis() + 10000;
		counter = 3;
		while (isAutonomous() && isEnabled()) {
			Subsystems.pneumatics.tick();
			standardAuto();
		}
	}

	/**
	 * This function is called once each time the robot enters operator control.
	 */
	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {

			Subsystems.driveTrain.tick(Subsystems.controller1.getLeftYAxis(), Subsystems.controller1.getLeftXAxis(),
					Subsystems.controller1.getLeftAxisButton());

			Subsystems.pneumatics.tick();
			Subsystems.shooter.think(Subsystems.controller1.getAButton(), Subsystems.controller1.getBButton(),
					Subsystems.controller1.getXButton(), Subsystems.controller1.getYButton(),
					Subsystems.controller1.getRightBumper(), Subsystems.controller1.getRightAxisButton());

			Subsystems.climber.think(Subsystems.controller1.getLeftBumper());

			Subsystems.camera.think(Subsystems.controller1.getStartButton());

			Subsystems.smartDash.tick(Subsystems.controller1.getSelectButton(), "SUP");
		}
	}
	//autonomous routines and helper methods

	public void autoShoot() {
		if (counter > 0) {
			Subsystems.shooter.think(false, false, false, true, false, false);
		}
		if (counter > 0 && System.currentTimeMillis() > readyTime) {
			if (Subsystems.shooter.shoot()) {
				counter--;
			}
		}
	}

	private void standardAuto() {
		if (System.currentTimeMillis() < turnTime) {
			autoShoot();
		} else if (System.currentTimeMillis() < forwardTime) {
			Subsystems.driveTrain.tick(0, -.7, false);
		} else if (System.currentTimeMillis() < endTime) {
			Subsystems.driveTrain.tick(1, 0, false);
		} else {
			Subsystems.driveTrain.tick(0, 0, false);
		}
	}
}
