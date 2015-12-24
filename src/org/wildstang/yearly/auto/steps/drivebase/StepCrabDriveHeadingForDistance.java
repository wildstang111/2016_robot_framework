package org.wildstang.yearly.auto.steps.drivebase;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.yearly.robot.SwerveInputs;

/**
 *
 * @author Billy
 */
public class StepCrabDriveHeadingForDistance extends AutoStep
{

   private double leftX, leftY, desiredAngle, distance, heading, throttle;
   private long timeToDrive = 0;
   private long lastStart;
   private long elapsed = 0;
   
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
      timeToDrive = (long)distance;  // Calculate time to drive from the distance
   }

   @Override
   public void update()
   {
      long now = System.currentTimeMillis();
      elapsed += now - lastStart;
      lastStart = now;
      
      if (elapsed >= timeToDrive)
      {
         setFinished(true);
      }
      else
      {
         ((AnalogInput)Core.getInputManager().getInput(SwerveInputs.DRV_HEADING_X.getName())).setValue(leftX);
         ((AnalogInput)Core.getInputManager().getInput(SwerveInputs.DRV_HEADING_Y.getName())).setValue(leftY);
      }

   }

   @Override
   public String toString()
   {
      return "Driving at " + desiredAngle + " degrees for " + distance + " distance at " + throttle + " speed.";
   }
}
