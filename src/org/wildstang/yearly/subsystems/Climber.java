package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.outputs.AnalogOutput;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.outputs.DigitalOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

public class Climber implements Subsystem
{
   private boolean liftButton;
   private boolean liftButtonPrev;
   private boolean liftButtonChanged;
   private boolean winchButton;
   private boolean hook;
   private boolean hookButton;
   private boolean hookButtonPrev;
   private boolean hookButtonChanged;
   private boolean pistonlow;
   private boolean pistonhigh;
   private boolean invtOutputs;

   @Override
   public void inputUpdate(Input source)
   {
      if (source.getName().equals(WSInputs.LIFT_BUTTON.getName()))
      {
         liftButton = ((DigitalInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.WINCH_BUTTON.getName()))
      {
         winchButton = ((DigitalInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.HOOK_BUTTON.getName()))
      {
         hookButton = ((DigitalInput) source).getValue();
      }
      // if (liftButton != liftButtonPrev)
      // {
      // if (liftButtonChanged)
      // {
      // liftButtonChanged = false;
      // }
      // else
      // {
      // liftButtonChanged = true;
      // }
      // liftButtonChanged = liftButton;
      // }
      System.out.println("input update got called");

   }

   @Override
   public void init()
   {
      System.out.println("init got called");
      invtOutputs = false;
      winchButton = false;
      hookButtonChanged = false;
      hookButtonPrev = false;
      hook = false;
      liftButtonChanged = false;
      liftButtonPrev = false;
      pistonlow = false;
      pistonhigh = false;
      Core.getInputManager().getInput(WSInputs.LIFT_BUTTON.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.WINCH_BUTTON.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.HOOK_BUTTON.getName()).addInputListener(this);

   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update()
   {
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
      /* Flips pistons on or off when buttons are pressed */
      if (liftButtonChanged)
      {
         if (!pistonlow)
         {
            ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LOWPISTONS.getName())).setValue(true
                  ^ invtOutputs);
            pistonlow = true;
            System.out.println("Pistons Out");

         }
         else if (pistonlow)
         {
            ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LOWPISTONS.getName())).setValue(false
                  ^ invtOutputs);

            pistonlow = false;
            System.out.println("Pistons In");

         }
         if (!pistonhigh)
         {
            ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.HIGHPISTONS.getName())).setValue(true
                  ^ invtOutputs);
            pistonhigh = true;
            System.out.println("Pistons Out");

         }
         else if (pistonhigh)
         {
            ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.HIGHPISTONS.getName())).setValue(false
                  ^ invtOutputs);
            pistonhigh = false;
            System.out.println("Pistons In");

         }
      }
      /*
       * Runs the winch
       */
      if (winchButton)
      {
         ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.WINCH_FRONT.getName())).setValue(0.3);
         ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.WINCH_BACK.getName())).setValue(0.3);
         System.out.println("winching...");
      }
      if (hookButtonChanged)
      {
         if (hook)
         {
            ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.HOOKS.getName())).setValue(WsDoubleSolenoidState.REVERSE.ordinal());
            hook = false;
            System.out.println("Hooks in");
         }
         else if (!hook)
         {
            ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.HOOKS.getName())).setValue(WsDoubleSolenoidState.FORWARD.ordinal());
            hook = true;
            System.out.println("Hooks out");
         }
         else
         {
            System.out.println("WHAT IS GOING ON?!?!?!?!?!");
         }

      }
      liftButtonPrev = liftButton;
      hookButtonPrev= hookButton;
   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

}
