package net.aisd.martin.frc.imageprocessing;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVision.Rect;

/**
 * Lots of borrow code from the FRC samples. This has some incredibly complex 
 * complex code that shouldn't be changed without Neil's permission. Most of the
 * comments are from FRC's sample code. Some changes were made to make it OOP
 * as opposed to procedural code. Good luck and give yourself some time if you want
 * to read this!!
 * @author Neil
 */
public class ImageProcessing {
	/**
	 * FRC: Sample program to use NIVision to find rectangles in the scene that are illuminated
	 * by a LED ring light (similar to the model from FIRSTChoice). The camera sensitivity
	 * is set very low so as to only show light sources and remove any distracting parts
	 * of the image.
	 * 
	 * The CriteriaCollection is the set of criteria that is used to filter the set of
	 * rectangles that are detected. In this example we're looking for rectangles with
	 * a minimum width of 30 pixels and maximum of 400 pixels.
	 * 
	 * The algorithm first does a color threshold operation that only takes objects in the
	 * scene that have a bright green color component. Then a convex hull operation fills 
	 * all the rectangle outlines (even the partially occluded ones). Then a small object filter
	 * removes small particles that might be caused by green reflection scattered from other 
	 * parts of the scene. Finally all particles are scored on rectangularity, aspect ratio,
	 * and hollowness to determine if they match the target.
	 *
	 * Look in the VisionImages directory inside the project that is created for the sample
	 * images as well as the NI Vision Assistant file that contains the vision command
	 * chain (open it with the Vision Assistant)
	 *
	 * NEIL: (PIV's) Basically they check to make sure this is a target and not some
	 * other random rectangle
	*/
	
    final int XMAXSIZE = 24;
    final int XMINSIZE = 24;
    final int YMAXSIZE = 24;
    final int YMINSIZE = 48;
    final double xMax[] = {1, 1, 1, 1, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, 1, 1, 1, 1};
    final double xMin[] = {.4, .6, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, 0.6, 0};
    final double yMax[] = {1, 1, 1, 1, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, 1, 1, 1, 1};
    final double yMin[] = {.4, .6, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05,
								.05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05,
								.05, .05, .6, 0};
								
    final int RECTANGULARITY_LIMIT = 60;
    final int ASPECT_RATIO_LIMIT = 75;
    final int X_EDGE_LIMIT = 40;
    final int Y_EDGE_LIMIT = 60;
    
    final int X_IMAGE_RES = 320;          //X Image resolution in pixels, should be 160, 320 or 640
    //final double VIEW_ANGLE = 43.5;       //Axis 206 camera (We aren't using this one)
    final double VIEW_ANGLE = 48;       //Axis M1011 camera (We are using this one)
    
    AxisCamera camera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation
    
    public class Scores {
        double rectangularity;
        double aspectRatioInner;
        double aspectRatioOuter;
        double xEdge;
        double yEdge;
    }
	
	/**
	 * Creates our ImageProcessing object. This is the same as th inside of 
	 * RobotInit() in the sample code for image tracking. Gets some variables
	 * and sets some precedents. 
	 */
	public ImageProcessing(){
		AxisCamera.getInstance();
		cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(MeasurementType.IMAQ_MT_AREA, 500, 65535, false);  cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(MeasurementType.IMAQ_MT_AREA, 500, 65535, false);
	}
	
	public void GetTargets(){
		try {
			/**
			* Do the image capture with the camera and apply the algorithm described above. This
			* sample will either get images from the camera or from an image file stored in the top
			* level directory in the flash memory on the cRIO. The file name in this case is "testImage.jpg"
			* 
			*/
			ColorImage image = camera.getImage();     // comment if using stored images

			BinaryImage thresholdImage = image.thresholdHSV(60, 100, 90, 255, 20, 255);   // keep only red objects
			BinaryImage convexHullImage = thresholdImage.convexHull(false);          // fill in occluded rectangles
			BinaryImage filteredImage = convexHullImage.particleFilter(cc);           // filter out small particles

			//iterate through each particle and score to see if it is a target
			Scores scores[] = new Scores[filteredImage.getNumberParticles()];
			for (int i = 0; i < scores.length; i++) {
				ParticleAnalysisReport report = filteredImage.getParticleAnalysisReport(i);
				scores[i] = new Scores();

				scores[i].rectangularity = scoreRectangularity(report);
				scores[i].aspectRatioOuter = scoreAspectRatio(filteredImage,report, i, true);
				scores[i].aspectRatioInner = scoreAspectRatio(filteredImage, report, i, false);
				scores[i].xEdge = scoreXEdge(thresholdImage, report);
				scores[i].yEdge = scoreYEdge(thresholdImage, report);

				if(scoreCompare(scores[i], false)) {
					System.out.println("particle: " + i + "is a High Goal  centerX: " + report.center_mass_x_normalized + "centerY: " + report.center_mass_y_normalized);
					System.out.println("Distance: " + computeDistance(thresholdImage, report, i, false));
					
					CenterTarget.getInstance().setxLocation(report.center_mass_x_normalized);
					CenterTarget.getInstance().setyLocation(report.center_mass_y_normalized);
					CenterTarget.getInstance().setDistance(computeDistance(thresholdImage, report, i, false));
				} else if (scoreCompare(scores[i], true)) {
					System.out.println("particle: " + i + "is a Middle Goal  centerX: " + report.center_mass_x_normalized + "centerY: " + report.center_mass_y_normalized);
					System.out.println("Distance: " + computeDistance(thresholdImage, report, i, true));
					
					LeftTarget.getInstance().setxLocation(report.center_mass_x_normalized);
					LeftTarget.getInstance().setyLocation(report.center_mass_y_normalized);
					LeftTarget.getInstance().setDistance(computeDistance(thresholdImage, report, i, true));
				} else {
					System.out.println("particle: " + i + "is not a goal  centerX: " + report.center_mass_x_normalized + "centerY: " + report.center_mass_y_normalized);
					//System determined that this is not a target do not change any classes
				}

				System.out.println("rect: " + scores[i].rectangularity + "ARinner: " + scores[i].aspectRatioInner);
				System.out.println("ARouter: " + scores[i].aspectRatioOuter + "xEdge: " + scores[i].xEdge + "yEdge: " + scores[i].yEdge);	
			}

			/**
			 * all images in Java must be freed after they are used since they are allocated out
			 * of C data structures. Not calling free() will cause the memory to accumulate over
			 * each pass of this loop.
			 */
			filteredImage.free();
			convexHullImage.free();
			thresholdImage.free();
			image.free();

		} catch (AxisCameraException ex) {        // this is needed if the camera.getImage() is called
			ex.printStackTrace();
		} catch (NIVisionException ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	 * Not a class for use by the robot. This is used to find the length in pixels
	 * of the target to allow for more accurate testing. Look for getDistance()
	 * and getPosition() for Target Aquisition. (NOT YET IMPLEMENTED) 
	 */
	private double computeDistance (BinaryImage image, ParticleAnalysisReport report, int particleNumber, boolean outer) throws NIVisionException {
		double rectShort, height;
		int targetHeight;

		rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
		//using the smaller of the estimated rectangle short side and the bounding rectangle height results in better performance
		//on skewed rectangles
		height = Math.min(report.boundingRectHeight, rectShort);
		targetHeight = outer ? 29 : 21;

		return X_IMAGE_RES * targetHeight / (height * 12 * 2 * Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
    }
	
	private double scoreAspectRatio(BinaryImage image, ParticleAnalysisReport report, int particleNumber, boolean outer) throws NIVisionException {
		double rectLong, rectShort, aspectRatio, idealAspectRatio;

		rectLong = NIVision.MeasureParticle(image.image, particleNumber, false, MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
		rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
		idealAspectRatio = outer ? (62/29) : (62/20);	//Dimensions of goal opening + 4 inches on all 4 sides for reflective tape

		//Divide width by height to measure aspect ratio
		if(report.boundingRectWidth > report.boundingRectHeight){
			//particle is wider than it is tall, divide long by short
			aspectRatio = 100*(1-Math.abs((1-((rectLong/rectShort)/idealAspectRatio))));
		} else {
			//particle is taller than it is wide, divide short by long
			aspectRatio = 100*(1-Math.abs((1-((rectShort/rectLong)/idealAspectRatio))));
		}

		return (Math.max(0, Math.min(aspectRatio, 100.0)));		//force to be in range 0-100
    }
	
	private boolean scoreCompare(Scores scores, boolean outer){
		boolean isTarget = true;
		isTarget &= scores.rectangularity > RECTANGULARITY_LIMIT;
		
		if(outer){
			isTarget &= scores.aspectRatioOuter > ASPECT_RATIO_LIMIT;
		} else {
			isTarget &= scores.aspectRatioInner > ASPECT_RATIO_LIMIT;
		}
		
		isTarget &= scores.xEdge > X_EDGE_LIMIT;
		isTarget &= scores.yEdge > Y_EDGE_LIMIT;

		return isTarget;
    }
	
	private double scoreRectangularity(ParticleAnalysisReport report){
		if(report.boundingRectWidth*report.boundingRectHeight !=0){
			return 100*report.particleArea/(report.boundingRectWidth*report.boundingRectHeight);
		} else {
			return 0;
		}	
    }
	
	private double scoreXEdge(BinaryImage image, ParticleAnalysisReport report) throws NIVisionException {
		double total = 0;
		LinearAverages averages;

		Rect rect = new Rect(report.boundingRectTop, report.boundingRectLeft, report.boundingRectHeight, report.boundingRectWidth);
		averages = NIVision.getLinearAverages(image.image, LinearAverages.LinearAveragesMode.IMAQ_COLUMN_AVERAGES, rect);
		float columnAverages[] = averages.getColumnAverages();
		
		for(int i=0; i < (columnAverages.length); i++){
			if(xMin[(i*(XMINSIZE-1)/columnAverages.length)] < columnAverages[i] 
					&& columnAverages[i] < xMax[i*(XMAXSIZE-1)/columnAverages.length]){
				total++;
			}
		}
		
		total = 100*total/(columnAverages.length);
		return total;
    }
	
	private double scoreYEdge(BinaryImage image, ParticleAnalysisReport report) throws NIVisionException {
		double total = 0;
		LinearAverages averages;

		Rect rect = new Rect(report.boundingRectTop, report.boundingRectLeft, report.boundingRectHeight, report.boundingRectWidth);
		averages = NIVision.getLinearAverages(image.image, LinearAverages.LinearAveragesMode.IMAQ_ROW_AVERAGES, rect);
		float rowAverages[] = averages.getRowAverages();
		
		for(int i=0; i < (rowAverages.length); i++){
			if(yMin[(i*(YMINSIZE-1)/rowAverages.length)] < rowAverages[i] 
					&& rowAverages[i] < yMax[i*(YMAXSIZE-1)/rowAverages.length]){
				total++;
			}
		}
		
		total = 100*total/(rowAverages.length);
		return total;
    }
	
	/*
	 * These classes will track certain targets and attempt to line the robot up
	 * with them.
	 * Returns an array of doubles that correspond to the location of the target.
	 * The drivetrain class will use this information to move the robot accordingly
	 * 
	 * 0 - Distance
	 * 1 - X Position
	 * 2 - Y Position
	 */
	
	public double[] trackLeftTarget(){
		double[] location = new double[3];
		
		location[0] = LeftTarget.getInstance().getDistance();
		location[1] = LeftTarget.getInstance().getxLocation();
		location[2] = LeftTarget.getInstance().getyLocation();
		return location;
	}
	
	public double[] trackCenterTarget(){
		double[] location = new double[3];
		
		location[0] = CenterTarget.getInstance().getDistance();
		location[1] = CenterTarget.getInstance().getxLocation();
		location[2] = CenterTarget.getInstance().getyLocation();
		return location;
	}
	
	public double[] trackRightTarget(){
		double[] location = new double[3];
		
		location[0] = RightTarget.getInstance().getDistance();
		location[1] = RightTarget.getInstance().getxLocation();
		location[2] = RightTarget.getInstance().getyLocation();
		return location;
	}
	
}
