package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.outputs.AnalogOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter implements Subsystem
{
   private boolean flyWheelToggle = false;
   private boolean PID = false;
   private double speedMod = 1.0;
   private double leftSpeed, rightSpeed;
   private double targetSpeed;
   CANTalon flyWheel;

   @Override
   public void inputUpdate(Input source)
   {
      // TODO Auto-generated method stub
      if (source.getName().equals(WSInputs.DRV_BUTTON_7.getName()))
      {
         // driver button 4 (triangle)
         if (flyWheelToggle == true)
         {
            flyWheelToggle = false;
         }
         else if (flyWheelToggle == false)
         {
            flyWheelToggle = true;
         }
         else
         {
            flyWheelToggle = true;
         }
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_1.getName()))
      {
         speedMod = 1.0;
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_2.getName()))
      {
         speedMod = .75;
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_3.getName()))
      {
         speedMod = .5;
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_4.getName()))
      {
         speedMod = .25;
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_5.getName()))
      {
         speedMod += 0.02;
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_6.getName()))
      {
         speedMod -= 0.02;
      }
      else if (source.getName().equals(WSInputs.DRV_THROTTLE.getName()))
      {
         leftSpeed = ((AnalogInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.DRV_RIGHT_Y.getName()))
      {
         rightSpeed = ((AnalogInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_8.getName()))
      {
         PID = !PID;
      }
   }

   @Override
   public void init()
   {
      // TODO Auto-generated method stub
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_1.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_3.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_4.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_5.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_6.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_7.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_8.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_RIGHT_Y.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_THROTTLE.getName()).addInputListener(this);
      flyWheel = new CANTalon(1);
      // flyWheel.changeControlMode(TalonControlMode.Speed);
      flyWheel.changeControlMode(TalonControlMode.PercentVbus);
      flyWheel.setFeedbackDevice(FeedbackDevice.QuadEncoder);
      flyWheel.configNominalOutputVoltage(+0.0f, -0.0f);
      flyWheel.configPeakOutputVoltage(+12.0f, -12.0f);
      flyWheel.configEncoderCodesPerRev(256);
      flyWheel.reverseSensor(false);
      flyWheel.reverseOutput(true);
      flyWheel.setProfile(0);
      flyWheel.setF((0.25 * 1023) / (1000 * 1.70666666));
      flyWheel.setP((0.02 * 1023) / 500);
      flyWheel.enableControl();
      // flyWheel.setVoltageRampRate(rampRate);

      // (Ideal Rotations / min) X (1 min / 60 sec) X (1 sec / 10 TvelMeas) X
      // (1024 native units / rotation) =
      // (Ideal Rotations / min) X (1.70666666) = Feed Forward constant
      speedMod = .25;
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
      targetSpeed = (speedMod * rightSpeed) * 4000;
      if (flyWheelToggle == true)
      {
         flyWheel.changeControlMode(TalonControlMode.PercentVbus);
         flyWheel.set(rightSpeed * speedMod);
      }
      // else if(PID)
      // {
      // flyWheel.changeControlMode(TalonControlMode.Speed);
      // flyWheel.set(targetSpeed);
      // }
      else
      {
         flyWheel.set(0.0);
      }
      ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_LEFT.getName())).setValue(leftSpeed
            * speedMod);
      SmartDashboard.putNumber("TalonEncoder", flyWheel.getEncVelocity());
      SmartDashboard.putNumber("rightStick", rightSpeed);
      SmartDashboard.putNumber("speedMod", speedMod);

   }

   private void getAnalogInVelocity()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return "Shooter";
   }

}
