/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aisd.martin.frc2013;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 * @author Neil
 */
public class SmartDash {

	private boolean pressed;

	public SmartDash() {
		NetworkTable.getTable("SmartDash");
		pressed = false;
	}

	public void tick(boolean change, String track) {
		if (change) {
			pressed = true;
		} else if (pressed) {
			NetworkTable.getTable("SmartDash").putBoolean("Change", true);
		}

		if (track.compareTo("Top") == 0) {
			NetworkTable.getTable("SmartDash").putBoolean("TopTrack", true);
		} else {
			NetworkTable.getTable("SmartDash").putBoolean("TopTrack", false);
		}

		if (track.compareTo("Middle") == 0) {
			NetworkTable.getTable("SmartDash").putBoolean("MiddleTrack", true);
		} else {
			NetworkTable.getTable("SmartDash").putBoolean("MiddleTrack", false);
		}
	}
}
