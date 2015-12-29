package org.wildstang.yearly.subsystems.swerve;

public class FieldCentricDriveMode extends SwerveDriveMode {
	int robotOffsetAngle = 0; // This will be where the IMU compass value will go. We will scale it to be between -180 and 180.
	
	public SwerveBaseState calculateNewState(SwerveBaseState p_prevState, double... args) {
		SwerveBaseState dummy = super.calculateNewState(p_prevState, args);
		// create a dummy swerve state that does all the swerve calculations
		
		//Here we take the target angle it was going to be and subtract the offset from it to make it field centric.
		//I think that is all there is to it but I could be wrong, 
		dummy.getFrontLeft().setRotationAngle(limitAngle(dummy.getFrontLeft().getRotationAngle() - robotOffsetAngle));
		dummy.getFrontRight().setRotationAngle(limitAngle(dummy.getFrontRight().getRotationAngle() - robotOffsetAngle));
		dummy.getRearLeft().setRotationAngle(limitAngle(dummy.getRearLeft().getRotationAngle() - robotOffsetAngle));
		dummy.getRearRight().setRotationAngle(limitAngle(dummy.getRearRight().getRotationAngle() - robotOffsetAngle));
		
		
		return dummy;
	}
	
	 private int limitAngle(int oldAngle) {
		   int newAngle = oldAngle;
		   while(newAngle >= 360) {
			   newAngle -= 360;
		   }
		   while(newAngle < 0) {
			   newAngle += 360;
		   }
		   
		   return newAngle;
	   }

	
}
