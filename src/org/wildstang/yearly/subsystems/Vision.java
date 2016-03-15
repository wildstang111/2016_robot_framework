package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.RemoteAnalogInput;
import org.wildstang.framework.io.inputs.RemoteDigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.WSInputs;

public class Vision implements Subsystem 
{
   private double distanceToTarget;
   private double angleToRotate;
   private boolean isOnTarget;
   
   
   @Override
   public void inputUpdate(Input source)
   {
      // TODO Auto-generated method stub
      if (source.getName().equals(WSInputs.CAMERA_DISTANCE.getName())) {
         distanceToTarget = ((RemoteAnalogInput) source).getValue();
      } else if (source.getName().equals(WSInputs.CAMERA_ANGLE.getName())) {
         angleToRotate = ((RemoteAnalogInput) source).getValue();
      } else if (source.getName().equals(WSInputs.ON_TARGET.getName())) {
         isOnTarget = ((RemoteDigitalInput) source).getValue();
      }
   }

   @Override
   public void init()
   {
      // TODO Auto-generated method stub
      distanceToTarget = 0;
      angleToRotate = 0;
      isOnTarget = false;
      
      Core.getInputManager().getInput(WSInputs.CAMERA_ANGLE.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.CAMERA_DISTANCE.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.ON_TARGET.getName()).addInputListener(this);
   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return "Vision";
   }
   
   public double getAngleToRotate() {
      return angleToRotate;
   }
   
   public double getDistanceToTarget() {
      return distanceToTarget;
   }
   
   public boolean getOnTarget() {
      return isOnTarget;
   }

}