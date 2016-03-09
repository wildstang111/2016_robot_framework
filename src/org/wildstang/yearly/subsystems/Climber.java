package org.wildstang.yearly.subsystems;

import org.wildstang.framework.config.Config;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;
import org.wildstang.yearly.robot.WSSubsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber implements Subsystem
{
   /*
    * Climber Robot Class Authors: Wallace Butler and Lucas Papaioannou
    */
   private boolean arm;

   private double winchValue;
   private boolean winchRunning;

   private boolean hook;

   private int startDelay = 0;
   private int stopDelay = 0;
   private boolean brakeEngaged = true;
   private boolean override = false;
   private boolean rightArmTouch;
   private boolean leftArmTouch;
   private boolean Winched = false;
   
   private double armHelpSpeed;
   private double armHelpRunTime;
   private static final String armsUpOutSpeed = ".arm_up_out_speed";
   private static final String armsUpRunTime = ".arm_up_run_time";
   private static final double ARMOUTSPEED_DEFAULT = .2;
   private static final double ARMOUTTIME_DEFAULT = 1000.0;
   private double winchEndTime;

   private WsSolenoid brake;
   private WsDoubleSolenoid hooks;
   private WsSolenoid lowerArm;
   private WsSolenoid upperArm;
   private WsVictor leftWinch;
   private WsVictor rightWinch;

   @Override
   public void inputUpdate(Input source)
   {
      if (source.getName().equals(WSInputs.MAN_BUTTON_1.getName()))
      {
         if (((DigitalInput) source).getValue())
         {
            arm = !arm;
            if(true == arm){
               winchEndTime = Timer.getFPGATimestamp() + armHelpRunTime;
            }
         }
      }
      else if (source.getName().equals(WSInputs.MAN_RIGHT_JOYSTICK_Y.getName()))
      {

         winchValue = ((AnalogInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_2.getName()))
      {
         if (((DigitalInput) source).getValue())
         {
            hook = !hook;
         }

      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_9.getName()))
      {
         if (((DigitalInput) source).getValue())
         {
            override = true;
         }
      }
      else if (source.getName().equals(WSInputs.LEFT_ARM_TOUCHING.getName()))
      {
         leftArmTouch = ((DigitalInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.RIGHT_ARM_TOUCHING.getName()))
      {
         rightArmTouch = ((DigitalInput) source).getValue();
      }
   }

   @Override
   public void init()
   {
      Core.getInputManager().getInput(WSInputs.MAN_RIGHT_JOYSTICK_Y.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_1.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_9.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.RIGHT_ARM_TOUCHING.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.LEFT_ARM_TOUCHING.getName()).addInputListener(this);
      brake = ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.WINCH_BRAKE.getName()));
      hooks = (WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.HOOK_EXTENSION.getName());
      lowerArm = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LOWER_ARM.getName());
      upperArm = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.UPPER_ARM.getName());
      leftWinch = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.WINCH_LEFT.getName());
      rightWinch = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.WINCH_RIGHT.getName());
      
      armHelpSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + armsUpOutSpeed, ARMOUTSPEED_DEFAULT);

      armHelpRunTime = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + armsUpRunTime, ARMOUTTIME_DEFAULT);
   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update()
   {
      if (arm)
      {
         if(Timer.getFPGATimestamp() < winchEndTime){
            leftWinch.setValue(-armHelpSpeed);
            rightWinch.setValue(-armHelpSpeed);
         }else{
            leftWinch.setValue(0);
            rightWinch.setValue(0);
         }
         
         protectIntake();
         resetIntakeToggle();
         if(!Winched)
         {
         upperArm.setValue(true);
         lowerArm.setValue(true);
         if (!hook)
         {
            hooks.setValue(WsDoubleSolenoidState.REVERSE.ordinal());
         }
         else
         {
            hooks.setValue(WsDoubleSolenoidState.FORWARD.ordinal());
         }
         }
         if (!hook)
         {
            hooks.setValue(WsDoubleSolenoidState.REVERSE.ordinal());
         }
         else
         {
            hooks.setValue(WsDoubleSolenoidState.FORWARD.ordinal());
         }
      }

      else
      {
         Winched = false;
         winchValue = 0;
         winchRunning = false;
         brakeEngaged = true;
         brake.setValue(true);
         upperArm.setValue(false);
         lowerArm.setValue(false);
      }

      if (!override)
      {
         if(!Winched && arm)
         {
            brakeEngaged = false;
            brake.setValue(false);
         }
         else if ((winchValue < .1) && (winchValue > -.1))
         {
            winchValue = 0.0;
            if (!brakeEngaged)
            {
               winchRunning = false;
               stopDelay++;
               if (stopDelay == 3)
               {
                  brake.setValue(true);
                  brakeEngaged = true;
                  stopDelay = 0;
               }
            }
         }
         else
         {
            if (!winchRunning)
            {
               winchValue = 0.0;
               brakeEngaged = false;
               brake.setValue(false);
               startDelay++;
               if (startDelay == 3)
               {
                  winchRunning = true;
                  startDelay = 0;
               }
            }
         }

         if(arm && Math.abs(winchValue) > 0)
         {
            Winched = true;
            upperArm.setValue(false);
            lowerArm.setValue(false);
            leftWinch.setValue(winchValue/2);
            rightWinch.setValue(winchValue/2);
         }
         else
         {
            leftWinch.setValue(winchValue/2);
            rightWinch.setValue(winchValue/2);
         }


      }
      else
      {
         winchValue = 0.0;
         brakeEngaged = true;
         winchRunning = false;
         brake.setValue(true);
         rightWinch.setValue(0.0);
         leftWinch.setValue(0.0);
      }
      
      SmartDashboard.putBoolean("Arm", arm);
      SmartDashboard.putBoolean("hookState", hook);
      SmartDashboard.putNumber("Winch Value", winchValue);
      SmartDashboard.putBoolean("Override", override);
      SmartDashboard.putBoolean("Winch Running", winchRunning);
      SmartDashboard.putBoolean("brakeEngaged", brakeEngaged);
      // SmartDashboard.putBoolean("Override", override);
      SmartDashboard.putBoolean("Right Arm", upperArm.getValue());
      SmartDashboard.putBoolean("Left Arm", lowerArm.getValue());
      SmartDashboard.putBoolean("Winch check", Winched);
   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return "Climber";
   }
   
   public void notifyConfigChange(Config p_newConfig)
   {
      armHelpSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + armsUpOutSpeed, ARMOUTSPEED_DEFAULT);

      armHelpRunTime = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + armsUpRunTime, ARMOUTTIME_DEFAULT);

   }
   
   public void protectIntake()
   {
      if(((Intake)Core.getSubsystemManager().getSubsystem(WSSubsystems.INTAKE.getName())).isDeployed() != true)
      {
      ((DigitalInput)Core.getInputManager().getInput(WSInputs.MAN_BUTTON_8.getName())).setValue(true);
      }
   }
   
   public void resetIntakeToggle()
   {
      ((DigitalInput)Core.getInputManager().getInput(WSInputs.MAN_BUTTON_8.getName())).setValue(false);
   }

}
