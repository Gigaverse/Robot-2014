package net.aisd.martin.frc2013;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Hadi
 */
public class PClimber {

	private DoubleSolenoid climbPiston;

	public PClimber(int pneumaticsSlot, int pistonF, int pistonB) {
		this.climbPiston = new DoubleSolenoid(pneumaticsSlot, pistonB, pistonF);
	}

	public void extend() {
		climbPiston.set(DoubleSolenoid.Value.kForward);
	}

	public void retract() {
		climbPiston.set(DoubleSolenoid.Value.kReverse);
	}

	public void think(boolean extend) {
		if (extend) {
			extend();
		} else {
			retract();
		}
	}
}