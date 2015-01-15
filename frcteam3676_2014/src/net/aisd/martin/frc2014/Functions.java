package net.aisd.martin.frc2014;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // make a logger function MR. SEB

public class Functions {

    public static boolean readyToShoot = true;
    public static boolean readyToGrab = true;
    public static boolean readyToRetract = true;
    public static boolean wreadyToGrab = false;
    public static boolean wreadyToRetract = false;
    public static boolean readyToChangeWheelState = true;
    public static int shotsFired = 0;

    public static void grabber(boolean grab) {
        if (grab == true && ListenHandlerEmulator.temp2 == false) {
            ListenHandlerEmulator.temp2 = true;
            Functions.log("8-> " + Values.sol8.get() + ", 7-> " + Values.sol7.get());
            if (Values.sol8.get() && readyToGrab) {
                readyToGrab = false;
                Functions.log("Grabbing");
                Values.sol8.set(false);
                Values.sol7.set(true);
                wreadyToGrab = true;
                Timer.delay(0.2);
                wreadyToGrab = false;
                SmartDashboard.putBoolean("readyToGrab", false);
                readyToRetract = true;
                SmartDashboard.putBoolean("readyToRetract", true);
                SmartDashboard.putBoolean("isGrabberUp", isGrabberUp());
            } else if (Values.sol7.get() && readyToRetract) {
                readyToRetract = false;
                SmartDashboard.putBoolean("readyToRetract", false);
                Functions.log("Retracting");
                Values.sol7.set(false);
                Values.sol8.set(true);
                SmartDashboard.putBoolean("readyToGrab", true);
                wreadyToRetract = true;
                Timer.delay(0.2);
                wreadyToRetract = false;
                readyToGrab = true;
                SmartDashboard.putBoolean("isGrabberUp", isGrabberUp());
            }
            ListenHandlerEmulator.temp2 = false;
        }
    }

    public static void spinGrabberWheel(int spin) {
        if (spin == 1) {
            SmartDashboard.putString("Ball motor", "FRWD");
            Values.grabberWheel.set(1);
        } else if (spin == 2) {
            SmartDashboard.putString("Ball motor", "BACK");
            Values.grabberWheel.set(-1);
        } else {
            SmartDashboard.putString("Ball motor", "STOP");
            Values.grabberWheel.set(0);
        }
    }

    public static void start_stopGrabberWheel(boolean startStop) { // OBSOLETE
        if (startStop && readyToChangeWheelState == true) {
            readyToChangeWheelState = false;
            Functions.log("Changing grabberWheel state -> " + Values.grabberWheel.getSpeed());
            if (Values.grabberWheel.getSpeed() == 0.0) {
                Functions.log("STARTING MOTOR");
                Values.grabberWheel.set(1);
            } else {
                Functions.log("STOPPING MOTOR");
                Values.grabberWheel.set(0);
            }
            Timer.delay(0.2);
            readyToChangeWheelState = true;
        }
    }

    public static void shooter(boolean fast, boolean medium, boolean slow) {
        // to retract turn 2/4/6
        boolean wasGrabberUp = false;
        if (slow) {
            Functions.log("SLOW shoot");
            shotsFired++;
            if (isGrabberUp()) { /* drop arm */
                Functions.log("Dropping arm before shooting");
                wasGrabberUp = true;
                Values.sol8.set(false);
                Values.sol7.set(true);
                Timer.delay(3);
            }
            Values.sol1.set(true);
            readyToShoot = false;
            Timer.delay(0.5);
            SmartDashboard.putBoolean("readyToShoot", false);
            if (wasGrabberUp) {
                Functions.log("Lifting arm back up");
                Values.sol8.set(true);
                Values.sol7.set(false);
            }
            Timer.delay(1);
            retractShooter();
        } else if (medium) {
            Functions.log("MEDIUM shoot");
            shotsFired++;
            if (isGrabberUp()) { /* drop arm */
                Functions.log("Dropping arm before shooting");
                wasGrabberUp = true;
                Values.sol8.set(false);
                Values.sol7.set(true);
                Timer.delay(3);
            }
            Values.sol1.set(true);
            Values.sol3.set(true);
            readyToShoot = false;
            SmartDashboard.putBoolean("readyToShoot", false);
            if (wasGrabberUp) {
                Functions.log("Lifting arm back up");
                Values.sol8.set(true);
                Values.sol7.set(false);
            }
            Timer.delay(1);
            retractShooter();
        } else if (fast) {
            Functions.log("FAST shoot");
            shotsFired++;
            if (isGrabberUp()) { /* drop arm */
                Functions.log("Dropping arm before shooting");
                wasGrabberUp = true;
                Values.sol8.set(false);
                Values.sol7.set(true);
                Timer.delay(3);
            }
            Values.sol1.set(true);
            Values.sol3.set(true);
            Values.sol5.set(true);
            readyToShoot = false;
            SmartDashboard.putBoolean("readyToShoot", false);
            if (wasGrabberUp) {
                Functions.log("Lifting arm back up");
                Values.sol8.set(true);
                Values.sol7.set(false);
            }
            Timer.delay(1);
            retractShooter();
        } else {
            // nothing is being pressed, we don't want to shoot anything
        }
    }

    public static void retractShooter() {
        Values.sol1.set(false);
        Values.sol3.set(false);
        Values.sol5.set(false);
        Values.sol2.set(true);
        Values.sol4.set(true);
        Values.sol6.set(true);
        Timer.delay(0.5);
        Values.sol2.set(false);
        Values.sol4.set(false);
        Values.sol6.set(false);
        readyToShoot = true;
        SmartDashboard.putBoolean("readyToShoot", true);
    }

    public static boolean isGrabberUp() {
        return !Values.sol7.get() && Values.sol8.get();
    }

    public static void log(String text) {
        // DO LOG
        Values.logText = "LOG#" + Values.log + "# " + text + "\n" + Values.logText;
        SmartDashboard.putString("LOG", Values.logText);
        Values.log++;
    }
}