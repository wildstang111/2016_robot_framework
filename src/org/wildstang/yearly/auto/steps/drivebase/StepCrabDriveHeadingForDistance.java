package org.wildstang.yearly.auto.steps.drivebase;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.InputManager;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.yearly.robot.SwerveInputs;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.SwerveDrive;

/**
 *
 * @author Billy
 */
public class StepCrabDriveHeadingForDistance extends AutoStep
{

   private double leftX, leftY, desiredAngle, distance, heading, throttle;
   
   public StepCrabDriveHeadingForDistance(double throttle, double heading, double distance)
   {
      this.desiredAngle = heading;
      this.distance = distance;
      this.throttle = throttle;
      this.leftX = throttle * Math.cos(heading);
      this.leftY = throttle * Math.sin(heading);
   }

   @Override
   public void initialize()
   {
//      ((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).overrideThrottleValue(throttle);
//      ((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).overrideHeadingValue(heading);
       ((AnalogInput)Core.getInputManager().getInput("Driver heading X")).setValue(leftX);
       ((AnalogInput)Core.getInputManager().getInput("Driver heading Y")).setValue(leftY);
	   finished = true;
   }

   @Override
   public void update()
   {
   }

   @Override
   public String toString()
   {
      return "Driving at " + desiredAngle + " degrees for " + distance + " distance at " + throttle + " speed.";
   }
}
