package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.RemoteAnalogInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.WSInputs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision implements Subsystem
{
//   private double distanceToTarget;
//   private double angleToRotateX;
//   private double angleToRotateY;
//   private double angleToRotateZ;
//   private double angleToRotate;
//   private boolean isOnTarget;
   private int rotateInt = 7;
   
   @Override
   public void inputUpdate(Input source)
   {
      // TODO Auto-generated method stub
      if (source.getName().equals(WSInputs.ROTATION_INTEGER.getName())) {
       rotateInt = (int) ((RemoteAnalogInput) source).getValue();
       }
      
//      if (source.getName().equals(WSInputs.CAMERA_DISTANCE.getName())) {
//         distanceToTarget = ((RemoteAnalogInput) source).getValue();
//      } else if (source.getName().equals(WSInputs.CAMERA_ANGLE_X.getName())) {
//         angleToRotateX = ((RemoteAnalogInput) source).getValue();
//      } else if (source.getName().equals(WSInputs.CAMERA_ANGLE_Y.getName())) {
//         angleToRotateY = ((RemoteAnalogInput) source).getValue();
//      } else if (source.getName().equals(WSInputs.CAMERA_ANGLE_Z.getName())) {
//         angleToRotateZ = ((RemoteAnalogInput) source).getValue();
//      } else if (source.getName().equals(WSInputs.ON_TARGET.getName())) {
//         isOnTarget = ((RemoteDigitalInput) source).getValue();
//      }
//      else if (source.getName().equals(WSInputs.ANGLE_OF_ROTATION.getName())) {
//         angleToRotate = ((RemoteAnalogInput) source).getValue();
//      }
   }

   @Override
   public void init()
   {
      // TODO Auto-generated method stub
//      distanceToTarget = 0;
//      angleToRotateX = 0;
//      angleToRotateY = 0;
//      angleToRotateZ = 0;
//      angleToRotate = 0;
//      isOnTarget = false;

//      Core.getInputManager().getInput(WSInputs.CAMERA_ANGLE_X.getName()).addInputListener(this);
//      Core.getInputManager().getInput(WSInputs.CAMERA_ANGLE_Y.getName()).addInputListener(this);
//      Core.getInputManager().getInput(WSInputs.CAMERA_ANGLE_Z.getName()).addInputListener(this);
//      Core.getInputManager().getInput(WSInputs.ANGLE_OF_ROTATION.getName()).addInputListener(this);
//      Core.getInputManager().getInput(WSInputs.CAMERA_DISTANCE.getName()).addInputListener(this);
//      Core.getInputManager().getInput(WSInputs.ON_TARGET.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.ROTATION_INTEGER.getName()).addInputListener(this);
      
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
      SmartDashboard.putNumber("Rotation Integer", rotateInt);
//      SmartDashboard.putNumber("Camera Distance", distanceToTarget);
//      SmartDashboard.putNumber("Camera Angle", angleToRotate);
//      SmartDashboard.putBoolean("On Target?", isOnTarget);
   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return "Vision";
   }
   
   public int getRotateInt() {
      return rotateInt;
   }
   
//   public double getAngleToRotateX() {
//      return angleToRotateX;
//   }
//   
//   public double getAngleToRotateY() {
//      return angleToRotateY;
//   }
//   
//   public double getAngleToRotateZ() {
//      return angleToRotateZ;
//   }
//   
//   public double getDistanceToTarget() {
//      return distanceToTarget;
//   }
//   
//   public boolean getOnTarget() {
//      return isOnTarget;
//   }

}
