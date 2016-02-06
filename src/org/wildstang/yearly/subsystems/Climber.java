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

   private int extendDelay = 0;
   private int retractDelay = 0;
   private boolean brakeEngaged;
   private boolean override = false;
   private boolean rightArmTouch;
   private boolean leftArmTouch;
   private boolean displayingLeft;
   private boolean displayingRight;

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
         // Deadband for winch that allows speed adjustments
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
      arm = false;
      displayingLeft = false;
      displayingRight = false;
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
      // so that the change doesn't carry over into the next cycle
      liftButtonChanged = false;
      hookButtonChanged = false;
      // checks whether the previous value is the same as now, and if the button
      // is currently pressed, indicates the change to true.
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
      // checks the arm button is pressed and if so it flips the state of the
      // arm
      if (liftButtonChanged)
      {
         if (!arm)
         {
            arm = true;
            System.out.println("pistons out");

         }
         else if (arm)
         {
            arm = false;
            System.out.println("pistons in");

         }
      }
      /*
       * Runs the winch
       */
      // makes sure we haven't already overridden the winch brake
      if (!override)
      {
         // checks the delays to see if it is time to either engage the brake or
         // run the winch
         if (extendDelay == 2 || retractDelay == 2)
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
         // checks whether the driver wants to run the winch or stop the winch
         if (winchGettingInput && brakeEngaged && !arm)
         {
            ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.WINCH_BRAKE.getName())).setValue(WsDoubleSolenoidState.REVERSE.ordinal());
            brakeEngaged = false;
            retractDelay++;
         }
         else if (!winchGettingInput && !brakeEngaged)
         {
            winchRunning = false;
            extendDelay++;
         }
         else
         {
            // otherwise, it stops all counts
            extendDelay = 0;
            retractDelay = 0;
         }
         // sets the winch speeds, but not if the arm is out
         if (winchRunning && !brakeEngaged && !arm)
         {
            ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.WINCH_LEFT.getName())).setValue(winchValue);
            ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.WINCH_RIGHT.getName())).setValue(winchValue);
         }
      }

      /*
       * Flips hooks when button pressed
       */
      // checks the state of the hooks to see if it is changed, then either
      // extends or retracts them based on their current state
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
      // engages the brake if the override is pressed
      if (override)
      {
         System.out.println("override engaged");

         brakeEngaged = true;
         ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.WINCH_BRAKE.getName())).setValue(WsDoubleSolenoidState.FORWARD.ordinal());
      }
      // constantly outputs true to the solenoids (if it should get true) so
      // that they don't just retract instantly
      if (arm)
      {
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LOWER_ARM.getName())).setValue(true);
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.UPPER_ARM.getName())).setValue(true);
      }
      // sets previous values for next cycle
      liftButtonPrev = liftButton;
      hookButtonPrev = hookButton;
      //checks whether the state for the touches has changed before displaying the change
      if (displayingRight != rightArmTouch)
      {
         SmartDashboard.putBoolean("Right Arm", rightArmTouch);
         displayingRight = rightArmTouch;
      }
      if (displayingLeft != leftArmTouch)
      {
         SmartDashboard.putBoolean("Left Arm", leftArmTouch);
         displayingLeft = leftArmTouch;

      }
   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

}