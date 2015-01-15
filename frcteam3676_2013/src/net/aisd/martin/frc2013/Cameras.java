package net.aisd.martin.frc2013;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author Neil
 */
public class Cameras {

	private Relay lightController;
	private boolean pressed;
	private boolean light;

	public Cameras(int slot, int yAxis, int xAxis, int light) {
		this.lightController = new Relay(light);
		pressed = false;
		this.light = true;
	}

	public void think(boolean lightOff) {

		//Conrols light
		//Allows you to hold button as long as needed
		if (light) {
			lightController.set(Relay.Value.kForward);
		} else {
			lightController.set(Relay.Value.kOff);
		}
		if (lightOff) {
			pressed = true;
		}
		if (!lightOff && pressed) {
			if (light) {
				light = !light;
			} else {
				light = !light;
			}
                        pressed = false;
		}
	}
}
