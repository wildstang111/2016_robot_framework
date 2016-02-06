package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.outputs.AnalogOutput;
import org.wildstang.framework.io.inputs.AnalogInput;
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
   private boolean liftButton;
   private boolean liftButtonPrev;
   private boolean liftButtonChanged;
   private double winchValue;
   private boolean winchGettingInput;
   private boolean winchRunning;
   private boolean hook;
   private boolean hookButton;
   private boolean hookButtonPrev;
   private boolean hookButtonChanged;
   private boolean pistonlow;
   private boolean pistonhigh;
   private int count = 0;
   private boolean brakeEngaged;
   private boolean override = false;
   private double winchSpeed;
   private boolean rightArmTouch;
   private boolean leftArmTouch;

   @Override
   public void inputUpdate(Input source)
   {
      if (source.getName().equals(WSInputs.MAN_BUTTON_1.getName()))
      {
         // Climb down
         liftButton = ((DigitalInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.MAN_RIGHT_JOYSTICK_Y.getName()))
      {
         if (((AnalogInput) source).getValue() < .1
               || ((AnalogInput) source).getValue() > -.1)
         {
            winchValue = ((AnalogInput) source).getValue();
            winchGettingInput = true;
         }
         else
         {
            winchValue = 0.0;
         }
      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_2.getName()))
      {
         // Climb up
         hookButton = ((DigitalInput) source).getValue();
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
      hookButtonChanged = false;
      hookButtonPrev = false;
      hook = false;
      liftButtonChanged = false;
      liftButtonPrev = false;
      pistonlow = false;
      pistonhigh = false;
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
      /*
       * Starts state change code
       */
      liftButtonChanged = false;
      hookButtonChanged = false;
      if (liftButton != liftButtonPrev && liftButton)
      {
         liftButtonChanged = true;

      }
      if (hookButton != hookButtonPrev && hookButton)
      {
         hookButtonChanged = true;

      }
      /*
       * Flips pistons on or off when buttons are pressed
       */
      if (liftButtonChanged)
      {
         if (!pistonlow && !pistonhigh)
         {
            pistonlow = true;
            pistonhigh = true;
            System.out.println("pistons out");

         }
         else if (pistonlow && pistonhigh)
         {
            pistonhigh = false;
            pistonlow = false;
            System.out.println("pistons in");

         }
         if (!pistonhigh && pistonlow)
         {
            pistonlow = false;
            System.out.println("Low pistons in");

         }
         else if (pistonhigh && !pistonlow)
         {
            pistonhigh = false;
            System.out.println("High pistons in");

         }
      }
      /*
       * Runs the winch
       */

      if (!override)
      {
         if (count == 2)
         {
            if (winchGettingInput && brakeEngaged)
            {
               winchRunning = true;
               System.out.println("Starting winch");

            }
            else if (!winchGettingInput && !brakeEngaged)
            {
               System.out.println("brakes engaged");
               brakeEngaged = true;
               ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.WINCH_BRAKE.getName())).setValue(WsDoubleSolenoidState.FORWARD.ordinal());
            }
         }

         if (winchGettingInput && brakeEngaged && !pistonlow && !pistonhigh)
         {
            ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.WINCH_BRAKE.getName())).setValue(WsDoubleSolenoidState.REVERSE.ordinal());
            brakeEngaged = false;
            count++;
         }
         else if (!winchGettingInput && !brakeEngaged)
         {
            winchRunning = false;
            count++;
         }
         else
         {
            count = 0;
         }

         if (winchRunning && !brakeEngaged && !pistonlow && !pistonhigh)
         {
            ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.WINCH_LEFT.getName())).setValue(winchValue);
            ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.WINCH_RIGHT.getName())).setValue(winchValue);
         }
      }


      /*
       * Flips hooks when button pressed
       */
      if (hookButtonChanged)
      {
         if (hook)
         {
            ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.HOOK_EXTENSION.getName())).setValue(WsDoubleSolenoidState.REVERSE.ordinal());
            hook = false;
            System.out.println("Hooks in");
         }
         else
         {
            ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.HOOK_EXTENSION.getName())).setValue(WsDoubleSolenoidState.FORWARD.ordinal());
            hook = true;
            System.out.println("Hooks out");
         }
      }
      if (override)
      {
         System.out.println("override engaged");

         brakeEngaged = true;
         ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.WINCH_BRAKE.getName())).setValue(WsDoubleSolenoidState.FORWARD.ordinal());
      }

      if (pistonhigh)
      {
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.UPPER_ARM.getName())).setValue(true);
      }
      if (pistonlow)
      {
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LOWER_ARM.getName())).setValue(true);
      }
      liftButtonPrev = liftButton;
      hookButtonPrev = hookButton;
      SmartDashboard.putBoolean("Left Arm", leftArmTouch);
      SmartDashboard.putBoolean("Right Arm", rightArmTouch);
      }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

}