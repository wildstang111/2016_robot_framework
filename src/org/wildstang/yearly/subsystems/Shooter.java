package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.outputs.DigitalOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
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
   private boolean currentFlySpeed, oldFlySpeed;
   private boolean currentFlyState, oldFlyState;
   private boolean flyHigh;
   private boolean hoodPosition = false;
   private boolean currentHoodState, oldHoodState;
   private double flySpeed;
   private double lowChange, highChange;
   private double targetSpeed;
   private double milsecToMax = 1000;
   private long startTime;
   private long sysTime;

   CANTalon flyWheel;

   @Override
   public void inputUpdate(Input source)
   {
      // TODO Auto-generated method stub
      if (source.getName().equals(WSInputs.MAN_BUTTON_3.getName()))
      {
         // manipulator button circle
         currentFlyState = ((DigitalInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_5.getName()))
      {
         // manipulator button R2
         currentHoodState = ((DigitalInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_4.getName()))
      {
         currentFlySpeed = ((DigitalInput) source).getValue();
      }
   }

   @Override
   public void init()
   {
      // TODO Auto-generated method stub
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_1.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_3.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_4.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_5.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_6.getName()).addInputListener(this);
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
   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub
      if (oldFlyState == false && currentFlyState == true)
      {
         if (flyWheelToggle == true)
         {
            flyWheelToggle = false;
            startTime = System.currentTimeMillis();
         }
         else if (flyWheelToggle == false)
         {
            flyWheelToggle = true;
            startTime = System.currentTimeMillis();
         }
      }
      oldFlyState = currentFlyState;

      if (oldHoodState == false && currentHoodState == true)
      {
         if (hoodPosition == true)
         {
            hoodPosition = false;
         }
         else if (hoodPosition == false)
         {
            hoodPosition = true;
         }
      }
      oldHoodState = currentHoodState;

      if (oldFlySpeed == false && currentFlySpeed == true)
      {
         if (flySpeed == .75)
         {
            flySpeed = .7;
         }
         else if (flySpeed == .7)
         {
            flySpeed = .75;
         }
         else
         {
            flySpeed = .75;
         }
      }
      oldFlySpeed = currentFlySpeed;

      if (flyWheelToggle == true)
      {
         flyWheel.set(flySpeed);
      }
      else
      {
         flyWheel.set(0);
      }

      // sysTime = System.currentTimeMillis();
      // targetSpeed = (speedMod * rightSpeed) * 4000;
      // if (flyWheelToggle == true)
      // {
      // if (sysTime < startTime + milsecToMax)
      // {
      // // flyWheel.changeControlMode(TalonControlMode.PercentVbus);
      // flyWheel.set((rightSpeed * speedMod)
      // * ((sysTime - startTime) / milsecToMax));
      // }
      // else
      // {
      // flyWheel.set((rightSpeed * speedMod));
      // }
      // }
      // else if(PID)
      // {
      // flyWheel.changeControlMode(TalonControlMode.Speed);
      // flyWheel.set(targetSpeed);
      // }
      // else
      // {
      // if (sysTime < startTime + milsecToMax)
      // {
      // flyWheel.set((rightSpeed * speedMod) - (rightSpeed * speedMod)
      // * ((sysTime - startTime) / milsecToMax));
      // }
      // else
      // {
      // flyWheel.set(0);
      // }
      // }
      ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.SHOOTER_HOOD.getName())).setValue(new Integer (hoodPosition == true ? 
            WsDoubleSolenoidState.FORWARD.ordinal() : WsDoubleSolenoidState.REVERSE.ordinal()));
      SmartDashboard.putNumber("TalonEncoderSpeed", flyWheel.getEncVelocity());
      SmartDashboard.putNumber("Fly Wheel Speed", flySpeed);
      // SmartDashboard.putNumber("rightStick", rightSpeed);
      // SmartDashboard.putNumber("speedMod", speedMod);
      SmartDashboard.putBoolean("Fly Wheel Toggle State", flyWheelToggle);
      SmartDashboard.putBoolean("Hood State", hoodPosition);
      SmartDashboard.putNumber("Fly Wheel Current", flyWheel.getOutputCurrent());
      SmartDashboard.putBoolean("Is fly up to speed?", doesSpeedMatch());
   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return "Shooter";
   }

   public void toggleShooterSpeed()
   {
      if (oldFlySpeed == false && currentFlySpeed == true)
      {
         if (flySpeed == .75)
         {
            flySpeed = .7;
            flyHigh = false;
         }
         else if (flySpeed == .7)
         {
            flySpeed = .75;
            flyHigh = true;
         }
         else
         {
            flySpeed = .75;
            flyHigh = true;
         }
      }
      oldFlySpeed = currentFlySpeed;
   }
   
   public boolean flySpeed()
   {
      return flyHigh;
   }
   
   public boolean hoodPos()
   {
      return hoodPosition;
   }
   
   public boolean isOn()
   {
      return currentFlyState;
   }

   public boolean doesSpeedMatch()
   {
      if (flySpeed == .7)
      {
         lowChange = .65;
         highChange = .75;
      }
      else if (flySpeed == .75)
      {
         lowChange = .7;
         highChange = .8;
      }

      if (flySpeed <= lowChange && flySpeed <= highChange)
      {
         return true;
      }
      else
      {
         return false;
      }
   }

}
