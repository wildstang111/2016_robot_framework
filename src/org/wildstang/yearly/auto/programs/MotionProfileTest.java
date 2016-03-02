package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveDistanceAtSpeed;

public class MotionProfileTest extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      // TODO Auto-generated method stub
      addStep(new StepDriveDistanceAtSpeed(36, 1, false));
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "MotionProfileTest";
   }

}
