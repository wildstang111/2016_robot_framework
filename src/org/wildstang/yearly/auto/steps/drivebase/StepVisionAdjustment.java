package org.wildstang.yearly.auto.steps.drivebase;


import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.DriveBase;
import org.wildstang.yearly.subsystems.Vision;

public class StepVisionAdjustment extends AutoStep
{
//   private double goalOffset, distance, angle, value;
   private int rotateInt;
   private boolean shouldFinish = false;

   public StepVisionAdjustment()
   {
      this.rotateInt = ((Vision) Core.getSubsystemManager().getSubsystem(WSSubsystems.VISION.getName())).getRotateInt();
   }
   
   @Override
   public void initialize()
   {
      // TODO Auto-generated method stub
      ((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).setThrottleValue(0);
//      ((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).overrideHeadingValue(value < 0 ? 0.6
//            : -0.6);
    ((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).overrideHeadingValue(rotateInt < 0 ? 0.2
    : -0.2);
// TODO
//      ((AnalogInput)Core.getInputManager().getInput(WSInputs.DRV_THROTTLE.getName())).setValue(0.0);
//      ((AnalogInput)Core.getInputManager().getInput(WSInputs.DRV_HEADING.getName())).setValue(value < 0 ? 0.6 : -0.6);
    ((AnalogInput)Core.getInputManager().getInput(WSInputs.DRV_THROTTLE.getName())).setValue(0.0);
    ((AnalogInput)Core.getInputManager().getInput(WSInputs.DRV_HEADING.getName())).setValue(rotateInt < 0 ? 0.2 : -0.2);
   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub
      if (shouldFinish)
      {
         setFinished(true);
         ((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).overrideHeadingValue(0.0);
         return;
      }
//      boolean aimed = ((Vision) Core.getSubsystemManager().getSubsystem(WSSubsystems.VISION.getName())).getOnTarget();
      rotateInt = ((Vision) Core.getSubsystemManager().getSubsystem(WSSubsystems.VISION.getName())).getRotateInt();
      if (rotateInt != 0)
      {
         
            // TODO
            ((AnalogInput)Core.getInputManager().getInput(WSInputs.DRV_THROTTLE.getName())).setValue(0.0);
//            ((AnalogInput)Core.getInputManager().getInput(WSInputs.DRV_HEADING.getName())).setValue(value < 0 ? 0.6 : -0.6);
            ((AnalogInput)Core.getInputManager().getInput(WSInputs.DRV_HEADING.getName())).setValue(rotateInt < 0 ? 0.2 : -0.2);
            shouldFinish = true;
      }
      else
      {
         if (rotateInt == 0)
         {
            ((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).overrideHeadingValue(0.0);

            // TODO: What would the equivalent be to set an input value for a
            // subsystem to react to, rather than set the output directly??
            // InputManager.getInstance().getOiInput(Robot.DRIVER_JOYSTICK).set(JoystickAxisEnum.DRIVER_HEADING,
            // new Double(0.0));
            shouldFinish = true;
         }
      }
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "StepVisionAdjustment";
   }

}
