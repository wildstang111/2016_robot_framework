/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.yearly.auto.steps.drivebase;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.subsystems.DriveBase;
import org.wildstang.yearly.subsystems.WSSubsystems;

/**
 *
 * @author Nathan
 */
public class StepStopDriveUsingMotionProfile extends AutoStep
{

   public StepStopDriveUsingMotionProfile()
   {
   }

   @Override
   public void initialize()
   {
   }

   @Override
   public void update()
   {
      ((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).stopStraightMoveWithMotionProfile();
      finished = true;
   }

   @Override
   public String toString()
   {
      return "Stop the drive using motion profile";
   }
}
