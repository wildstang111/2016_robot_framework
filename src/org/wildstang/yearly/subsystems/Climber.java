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

public class Climber implements Subsystem
{
   /*
    * Climber Robot Class Authors: Wallace Butler and Lucas Papaioannou
    */
   private boolean liftButton;
   private boolean liftButtonPrev;
   private boolean liftButtonChanged;
   private double winchValue;
   private boolean hook;
   private boolean hookButton;
   private boolean hookButtonPrev;
   private boolean hookButtonChanged;
   private boolean pistonlow;
   private boolean pistonhigh;
   private int count = 0;
   private boolean brakePressed;

   @Override
   public void inputUpdate(Input source)
   {
      if (source.getName().equals(WSInputs.MAN_BUTTON_2.getName()))
      {
         // Climb down
         liftButton = ((DigitalInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.MAN_RIGHT_JOYSTICK_Y.getName()))
      {
         winchValue = ((AnalogInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_4.getName()))
      {
         // Climb up
         hookButton = ((DigitalInput) source).getValue();
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
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_4.getName()).addInputListener(this);

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
            ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.HIGHPISTONS.getName())).setValue(true);
            ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LOWPISTONS.getName())).setValue(true);
            pistonlow = true;
            pistonhigh = true;
            System.out.println("pistons out");

         }
         else if (pistonlow && pistonhigh)
         {
            ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LOWPISTONS.getName())).setValue(false);
            ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.HIGHPISTONS.getName())).setValue(false);
            pistonhigh = false;
            pistonlow = false;
            System.out.println("pistons in");

         }
         if (!pistonhigh && pistonlow)
         {
            ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LOWPISTONS.getName())).setValue(false);
            pistonlow = false;
            System.out.println("Low pistons in");

         }
         else if (pistonhigh && !pistonlow)
         {
            ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.HIGHPISTONS.getName())).setValue(false);
            pistonhigh = false;
            System.out.println("High pistons in");

         }
      }
      /*
       * Runs the winch
       */
      
      count++;
      if(count%50==0){
         System.out.println(winchValue);
      }
      ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.WINCH_FRONT.getName())).setValue(winchValue);
      ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.WINCH_BACK.getName())).setValue(winchValue);
      /*
       * Flips hooks when button pressed
       */
      if (hookButtonChanged)
      {
         if (hook)
         {
            ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.HOOKS.getName())).setValue(WsDoubleSolenoidState.REVERSE.ordinal());
            hook = false;
            System.out.println("Hooks in");
         }
         else
         {
            ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.HOOKS.getName())).setValue(WsDoubleSolenoidState.FORWARD.ordinal());
            hook = true;
            System.out.println("Hooks out");
         }
      }
      if(brakePressed){
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LOWPISTONS.getName())).setValue(true);
      }
      liftButtonPrev = liftButton;
      hookButtonPrev = hookButton;
   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

}
