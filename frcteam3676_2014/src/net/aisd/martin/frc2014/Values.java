package net.aisd.martin.frc2014;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class Values {
    // the 8 solenoids, technically 4 doubles but the reverse code doesn't work
    public static Solenoid sol1; // SHOOT
    public static Solenoid sol2; // RETRACT
    public static Solenoid sol3; // SHOOT
    public static Solenoid sol4; // RETRACT
    public static Solenoid sol5; // SHOOT
    public static Solenoid sol6; // RETRACT
    public static Solenoid sol7; // GRAB
    public static Solenoid sol8; // RETRACT
    public static int log = 0;
    public static String logText = "";
    public static Victor grabberWheel;
}