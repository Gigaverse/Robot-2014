package net.aisd.martin.frc2013;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Empty Class till we know what to do with it
 *
 * @author Neil
 */
public class Shooter {

	private Talon front;
	private Talon back;
	private DoubleSolenoid loader;
        private DoubleSolenoid ejection;
	private long readyTime = 0;
        private long ejectTime = 0;
	private final static long reload_time = 1 * 1000;

	public Shooter(int slot, int front, int back,
			int pneumaticsSlot, int pistonF, int pistonB,
                        int eject) {
		this.front = new Talon(slot, front);
		this.back = new Talon(slot, back);
		this.loader = new DoubleSolenoid(pneumaticsSlot, pistonB, pistonF);
                this.ejection = new DoubleSolenoid(pneumaticsSlot, 7, eject);
                readyTime = 0;
	}

	public boolean shoot() {
		if (System.currentTimeMillis() < readyTime) {
			return false;
		}
		readyTime = System.currentTimeMillis() + reload_time;
		loader.set(DoubleSolenoid.Value.kReverse);
		return true;
	}

	public void spinUp() {
		front.set(-1);
		back.set(-1);
	}

	public void think(boolean quarter, boolean half, boolean most, boolean full, boolean fire, boolean eject) {
            //resets all the pistsons
            if (System.currentTimeMillis() > readyTime - .5 * 1000) {
			loader.set(DoubleSolenoid.Value.kForward);
            }
            if (System.currentTimeMillis() > ejectTime + reload_time){
                ejection.set(DoubleSolenoid.Value.kReverse);
            }

		if (quarter) {
			front.set(-.25);
			back.set(-.25);
		} else if (half) {
			front.set(-.5);
			back.set(-.5);
		} else if (most) {
			front.set(-.75);
			back.set(-.75);
		} else if (full) {
			front.set(-1);
			back.set(-1);
		} else {
			front.set(0);
			back.set(0);
		}

		if (fire) {
			shoot();
		}
                
                if(eject){
                    ejection.set(DoubleSolenoid.Value.kForward);
                    ejectTime = System.currentTimeMillis();
                }

		if (readyTime - System.currentTimeMillis() < 0) {
			SmartDashboard.putBoolean("Ready to Fire", true);
			SmartDashboard.putNumber("Time till fire", 0);
		} else {
			SmartDashboard.putBoolean("Ready to Fire", false);
			SmartDashboard.putNumber("Time till fire", (double) (readyTime - System.currentTimeMillis()) / 1000);
		}

	}
}
