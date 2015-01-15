/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aisd.martin.frc.imageprocessing;

/**
 *
 * @author Neil
 */
public class CenterTarget {
	private static CenterTarget centerTarget;
	
	/*
	 * Gives location and distance. For the purpose of this code firing solution
	 * is when the robot will hit with an accuracy of >=90%. For now it will just
	 * tell you when the target is centered and in range.
	 */
	private double xLocation;
	private double yLocation;
	private double distance;
	private boolean firingSolution;
	
	public static CenterTarget getInstance(){
		if(centerTarget == null)
			centerTarget = new CenterTarget();
		return centerTarget;
	}
	
	private CenterTarget(){
		xLocation = 0;
		yLocation = 0;
		distance = Double.POSITIVE_INFINITY;
		firingSolution = false;
	}

	public double getxLocation() {
		return xLocation;
	}

	public double getyLocation() {
		return yLocation;
	}

	public double getDistance() {
		return distance;
	}
	
	/*
	 * This one checks our values and returns wether or not it thinks it will make
	 * the shot.
	 * 
	 * Assumes shot is good at first then checks all the parameters given to it
	 * and decides if the shot is still good. Returns the final value
	 */
	public boolean haveFiringSolution() {
		firingSolution = true;
		
		if(Math.abs(xLocation) > .2)//TODO: update with a cone formula of some sort
			firingSolution = false;
		if(Math.abs(yLocation) > .2)//TODO: account for drop over distance and cone
			firingSolution = false;
		if(distance > Double.NEGATIVE_INFINITY) //TODO: Infinity will be replaced by max range
			firingSolution = false;
		return firingSolution;
	}

	public void setxLocation(double xLocation) {
		this.xLocation = xLocation;
	}

	public void setyLocation(double yLocation) {
		this.yLocation = yLocation;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
