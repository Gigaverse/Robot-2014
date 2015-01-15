package net.aisd.martin.frc2013;

import net.aisd.martin.frc.AISDCompressor;

/**
 * This is the class for all the hardware relating to pneumatics
 *
 * @author Neil
 */
public class Pneumatics {

	public AISDCompressor compressor;
	public DriveTrain driveTrain;

	/**
	 * This constructor uses the built in constants to find the slots and
	 * channels I dont see us using another one but just write it if you need it
	 */
	public Pneumatics() {
		this.compressor = new AISDCompressor(RobotMap.Pneumatics.slot, RobotMap.Pneumatics.pressure_switch,
				RobotMap.Pneumatics.slot, RobotMap.Pneumatics.compressor);
	}

	/*
	 * Anything in this class that needs to be updated each by the main thread 
	 * will go in this method. Will be called in the Team3676Robot operator control
	 * loop
	 * @param none
	 */
	public void tick() {
		/*
		 * TODO: none currently in fact this class is useless unless we use 
		 * pneumatics
		 */
	}
}
