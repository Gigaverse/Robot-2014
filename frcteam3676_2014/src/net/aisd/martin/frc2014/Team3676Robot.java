package net.aisd.martin.frc2014;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // make a logger function MR. SEB
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Relay;

public class Team3676Robot extends SimpleRobot {
    RobotDrive robotDrive;
    Joystick controller;
    Gyro gyro;
    MaxbotixUltrasonic ultrasonic;
    Relay ringLights;
    boolean lightsOn = true;
    boolean rotating = false;
    boolean doAutonomous1 = false;
    boolean doAutonomous2 = true;
    public void robotInit() {
        Functions.log("ROBOT STARTED");
        gyro = new Gyro(1);
        Values.grabberWheel = new Victor(5);
        controller = new Joystick(1);
        robotDrive = new RobotDrive(1,2,3,4);
        ringLights = new Relay(2);
        Compressor c = new Compressor(1,1);
        c.start();
        ultrasonic = new MaxbotixUltrasonic(2);
        Values.sol1 = new Solenoid(2,1); // 1
        Values.sol2 = new Solenoid(2,2); // 2
        Values.sol3 = new Solenoid(2,3); // 3
        Values.sol4 = new Solenoid(2,4); // 4
        Values.sol5 = new Solenoid(2,5); // 5
        Values.sol6 = new Solenoid(2,6); // 6
        Values.sol7 = new Solenoid(2,7); // 7
        Values.sol8 = new Solenoid(2,8); // 8
        SmartDashboard.putBoolean("readyToShoot", true);
        SmartDashboard.putBoolean("readyToGrab", true);
        SmartDashboard.putBoolean("isGrabberUp", Functions.isGrabberUp());
    }
    public void autonomous() { // 10 seconds
        // woohoo
        // this will run ONCE
        if (doAutonomous1) {
            while (isEnabled() && isAutonomous()) {
                Functions.log("Robot autonomousing");
                //Timer.delay(0.2);
                double s = 0;
                while (s < 1.60) {
                    robotDrive.mecanumDrive_Cartesian(0,-0.5,0,0.0);
                    Timer.delay(0.01);
                    s += 0.01;
                }
                robotDrive.mecanumDrive_Cartesian(0,0,0,0.0);
                Timer.delay(1);
                Functions.shooter(true, false, false);
                break;
            }
        } else if (doAutonomous2) {
            while (isEnabled() && isAutonomous()) {
                Functions.log("Robot autonomousing");
                Functions.grabber(true);
                Functions.spinGrabberWheel(1);
                Timer.delay(1.5);
                Functions.spinGrabberWheel(0);
                double s = 0;
                while (s <= 3.00) {
                    robotDrive.mecanumDrive_Cartesian(0,-0.35,0,0.0);
                    Timer.delay(0.01);
                    s += 0.01;
                }
                robotDrive.mecanumDrive_Cartesian(0,0,0,0.0);
                boolean dropping = true;
                if (dropping) {
                    Functions.spinGrabberWheel(-1);
                    Timer.delay(1.5);
                    Functions.spinGrabberWheel(0);
                }
                Functions.shooter(true, false, false);
                Functions.spinGrabberWheel(1);
                Timer.delay(2);
                Functions.grabber(true);
                Timer.delay(2);
                Functions.spinGrabberWheel(0);
                Functions.shooter(true, false, false);
                break;
            }
        }
    }
    public void disabled() { // this should be caled re-enabled
        if (Functions.wreadyToRetract) {
            Functions.readyToRetract = true;
        }
        if (Functions.wreadyToGrab) {
            Functions.readyToGrab = true;
        }
        SmartDashboard.putBoolean("readyToRetract", false);
        Functions.log("Retracting");
        Values.sol7.set(false);
        Values.sol8.set(true);
        SmartDashboard.putBoolean("readyToGrab", true);
        SmartDashboard.putBoolean("isGrabberUp", Functions.isGrabberUp());
        Timer.delay(1);
        Functions.log("Robot disabled");
    }
    public void operatorControl() {
        if (isEnabled()) {
            Functions.log("Robot enabled");
            ringLights.set(Relay.Value.kForward);
        }
        while (isOperatorControl() && isEnabled()) {
            if (controller.getRawButton(7)/*BACK*/== true ) {
                gyro.reset();
                gyro.setSensitivity(0.007);
            }
            
            if ((controller.getTwist() < -0.1) || (controller.getTwist() > 0.1))
                {
                    robotDrive.mecanumDrive_Cartesian(
                            (Math.abs(controller.getX()) > 0.2 ? controller.getX()*-1 : 0), 
                            (Math.abs(controller.getY()) > 0.2 ? controller.getY() : 0), 
                            controller.getTwist(), 
                            0.0
                    );
                    rotating = true;
            } else {
                    if (rotating == true)
                    {
                        gyro.reset();
                        rotating = false;
                    }
                    robotDrive.mecanumDrive_Cartesian(
                            (Math.abs(controller.getX()) > 0.2 ? controller.getX()*-1 : 0), 
                            (Math.abs(controller.getY()) > 0.2 ? controller.getY() : 0), 
                            controller.getTwist(), 
                            gyro.getAngle()
                    );        
            }
            
            robotDrive.mecanumDrive_Cartesian(
                    (Math.abs(controller.getX()) > 0.2 ? controller.getX()*-1 : 0), 
                    (Math.abs(controller.getY()) > 0.2 ? controller.getY() : 0), 
                    controller.getTwist(), 
                    0.0
            );
            
            Functions.grabber(controller.getRawButton(3)/*X*/);
            
            if (controller.getRawButton(6)/*LB*/) {
                Functions.spinGrabberWheel(1);
            } else if (controller.getRawButton(5)/*RB*/) {
                Functions.spinGrabberWheel(2);
            } else {
                Functions.spinGrabberWheel(0);
            }
            
            if (controller.getRawButton(10) && !ListenHandlerEmulator.temp1) {
                ListenHandlerEmulator.temp1 = true;
                if (ringLights.get() == Relay.Value.kForward) {
                    ringLights.set(Relay.Value.kOff);
                    Functions.log("Light turned off");
                } else {
                    ringLights.set(Relay.Value.kForward);
                    Functions.log("Light turned on");
                }
            } else if (!controller.getRawButton(10) && ListenHandlerEmulator.temp1) {
                ListenHandlerEmulator.temp1 = false;
            }
            
            Functions.shooter(controller.getRawButton(1)/*A*/, controller.getRawButton(2)/*B*/, controller.getRawButton(4)/*Y*/);
            if (controller.getRawAxis(6) != 0) {
                Functions.log("ura: " + ultrasonic.GetRangeInCM()/100 + " (" + ultrasonic.GetVoltage() + ")"); // in METERS
            }
            // there is no evident latency from these, it is possible they are asynchronous
            SmartDashboard.putString("ultrasonic", (ultrasonic.GetVoltage()) + " m"); // LET USER SETUP
            .JLP80SmartDashboard.putString("Gyro angle", gyro.getAngle() + " d"); // RESETS DUE TO SHAKINESS
            Timer.delay(0.01);
        }
    }
    public void test() { // drive ONLY, using X,Y,A,B buttons only 
        while (isEnabled()) {
            if (controller.getRawButton(1)/*A*/) {
                robotDrive.mecanumDrive_Cartesian(
                            0, 
                            -0.2, 
                            0, 
                            0.0
                    );
            } else if (controller.getRawButton(2)/*B*/) {
                robotDrive.mecanumDrive_Cartesian(
                            -0.2, 
                            0, 
                            0, 
                            0.0
                    );
            } else if (controller.getRawButton(3)/*X*/) {
                robotDrive.mecanumDrive_Cartesian(
                            0.2, 
                            0, 
                            0, 
                            0.0
                    );
            } else if (controller.getRawButton(4)/*Y*/) {
                robotDrive.mecanumDrive_Cartesian(
                            0, 
                            0.2, 
                            0, 
                            0.0
                    );
            }
            Timer.delay(0.01);
            robotDrive.mecanumDrive_Cartesian(
                            0, 
                            0, 
                            0, 
                            0.0
                    );
        }
    }
}