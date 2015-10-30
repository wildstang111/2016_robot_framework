package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.AutoSerialStepGroup;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.drivebase.StepStartDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.StepStopDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.StepWaitForDriveMotionProfile;

public class Drive extends AutoProgram
{
   protected final double DISTANCE = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".DistanceToDrive", 140.0);

   @Override
   protected void defineSteps()
   {

      AutoSerialStepGroup drive = new AutoSerialStepGroup("Drive");
      drive.addStep(new StepStartDriveUsingMotionProfile(DISTANCE, 1.0));
      drive.addStep(new StepWaitForDriveMotionProfile());
      drive.addStep(new StepStopDriveUsingMotionProfile());

      addStep(drive);
   }

   @Override
   public String toString()
   {
      return "Drive";
   }
}