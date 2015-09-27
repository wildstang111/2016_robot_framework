package org.wildstang.yearly.auto.steps.drivebase;

import org.wildstang.framework.core.Core;
import org.wildstang.fw.auto.steps.AutoStep;
import org.wildstang.yearly.subsystems.DriveBase;
import org.wildstang.yearly.subsystems.WSSubsystems;

public class StepStrafe extends AutoStep
{

   private double strafe;

   // Left is negative, right is positive
   public StepStrafe(double strafe)
   {
      this.strafe = strafe;
   }

   @Override
   public void initialize()
   {
      ((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).overrideStrafeValue(strafe);
      finished = true;
   }

   @Override
   public void update()
   {
   }

   @Override
   public String toString()
   {
      return "Strafing";
   }
}
