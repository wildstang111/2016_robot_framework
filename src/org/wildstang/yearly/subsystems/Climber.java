package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.outputs.AnalogOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

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
   private boolean inLoop = false;

   @Override
   public void inputUpdate(Input source)
   {
      if (source.getName().equals(WSInputs.MAN_BUTTON_1.getName()))
      {
         // Climb down
         if (((DigitalInput) source).getValue())
         {
            arm = !arm;
         }
      }
      else if (source.getName().equals(WSInputs.MAN_RIGHT_JOYSTICK_Y.getName()))
      {

         winchValue = ((AnalogInput) source).getValue();

      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_2.getName()))
      {
         // Climb up
         if (((DigitalInput) source).getValue())
         {
            hook = !hook;
         }

      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_9.getName()))
      {
         override = ((DigitalInput) source).getValue();
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
      /*
       * Sets default values and calls for inputs
       */
      System.out.println("init got called");
      Core.getInputManager().getInput(WSInputs.MAN_RIGHT_JOYSTICK_Y.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_1.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_9.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.RIGHT_ARM_TOUCHING.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.LEFT_ARM_TOUCHING.getName()).addInputListener(this);
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
         // ((WsSolenoid)
         // Core.getOutputManager().getOutput(WSOutputs.WINCH_BRAKE.getName())).setValue(true);
         // Maybe...?
         winchValue = 0;
         winchRunning = false;
         brakeEngaged = true;
         ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.WINCH_BRAKE.getName())).setValue(WsDoubleSolenoidState.FORWARD.ordinal());
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.UPPER_ARM.getName())).setValue(true);
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LOWER_ARM.getName())).setValue(true);
         SmartDashboard.putBoolean("liftState", arm);
      }
      else if (!arm)
      {
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.UPPER_ARM.getName())).setValue(false);
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LOWER_ARM.getName())).setValue(false);
         SmartDashboard.putBoolean("liftState", arm);
      }

      if (!override)
      {
         if (!(winchValue > .1 || winchValue < -.1))
         {
            winchValue = 0.0;
            if (!brakeEngaged)
            {
               winchRunning = false;
               stopDelay++;
               if (stopDelay == 3)
               {
                  ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.WINCH_BRAKE.getName())).setValue(WsDoubleSolenoidState.FORWARD.ordinal());
                  brakeEngaged = true;
                  stopDelay = 0;
               }

            }
         }
         else
         {
            if (winchRunning = false)
            {
               brakeEngaged = false;
               ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.WINCH_BRAKE.getName())).setValue(WsDoubleSolenoidState.REVERSE.ordinal());
               startDelay++;
               if (startDelay == 3)
               {
                  winchRunning = true;
                  startDelay = 0;
               }
            }

         }

         SmartDashboard.putBoolean("brakeEngaged", brakeEngaged);

      }

      if (!hook)
      {
         ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.HOOK_EXTENSION.getName())).setValue(WsDoubleSolenoidState.REVERSE.ordinal());
         SmartDashboard.putBoolean("hookState", hook);
      }
      else
      {
         ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.HOOK_EXTENSION.getName())).setValue(WsDoubleSolenoidState.FORWARD.ordinal());
         SmartDashboard.putBoolean("hookState", hook);
      }

      if (override)
      {
         System.out.println("override engaged");

         brakeEngaged = true;
         ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.WINCH_BRAKE.getName())).setValue(WsDoubleSolenoidState.FORWARD.ordinal());
      }

      SmartDashboard.putBoolean("Right Arm", rightArmTouch);
      SmartDashboard.putBoolean("Left Arm", leftArmTouch);

   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

}