package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.outputs.AnalogOutput;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.outputs.DigitalOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

public class Climber implements Subsystem
{
   private boolean liftButton;
   private boolean liftButton2;
   private boolean pistonlowrt;
   private boolean pistonlowlft;
   private boolean pistonhighrt;
   private boolean pistonhighlft;
   private boolean invtInputs;
   private boolean invtOutputs;
   private boolean invtWinch;
   private boolean winchInput;
   private double winchOutput;

   @Override
   public void inputUpdate(Input source)
   {
      if (source.getName().equals(WSInputs.LIFT_BUTTON_1.getName()))
      {
         liftButton = ((DigitalInput) source).getValue() ^ invtInputs;
      }
      else if (source.getName().equals(WSInputs.LIFT_BUTTON_2.getName()))
      {
         liftButton2 = ((DigitalInput) source).getValue() ^ invtInputs;
      }
      else if (source.getName().equals(WSInputs.WINCH_BUTTON.getName()))
      {
         winchInput = ((DigitalInput) source).getValue() ^ invtWinch;
      }

   }

   @Override
   public void init()
   {
      invtOutputs = false;
      invtInputs = false;
      invtWinch = false;
      pistonlowrt = false ^ invtInputs;
      pistonlowlft = false ^ invtInputs;
      pistonhighrt = false ^ invtInputs;
      pistonhighlft = false ^ invtInputs;
      Core.getInputManager().getInput(WSInputs.LIFT_BUTTON_1.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.LIFT_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.WINCH_BUTTON.getName()).addInputListener(this);

   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update()
   {
      /* Gives output of inputs for debugging */
      System.out.println("Lift 1: " + liftButton + "     Lift 2:" + liftButton2 + "     Winch:" + winchInput);

      /* Flips pistons on or off when buttons are pressed */
      if (liftButton)
      {
         if (!pistonlowrt && !pistonlowlft)
         {
            ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.RIGHTLOWPIS.getName())).setValue(true
                  ^ invtOutputs);
            ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.LEFTLOWPIS.getName())).setValue(true
                  ^ invtOutputs);
            pistonlowrt = true ^ invtOutputs;
            pistonlowlft = true ^ invtOutputs;

         }
         else if (pistonlowrt && pistonlowlft)
         {
            ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.RIGHTLOWPIS.getName())).setValue(false
                  ^ invtOutputs);
            ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.LEFTLOWPIS.getName())).setValue(false
                  ^ invtOutputs);
            pistonlowrt = false ^ invtOutputs;
            pistonlowlft = false ^ invtOutputs;
         }
      }
      if (liftButton2)
      {
         if (!pistonhighrt && !pistonhighlft)
         {
            ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.RIGHTHIGHPIS.getName())).setValue(true
                  ^ invtOutputs);
            ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.LEFTHIGHPIS.getName())).setValue(true
                  ^ invtOutputs);
            pistonhighrt = true ^ invtOutputs;
            pistonhighlft = true ^ invtOutputs;
         }
         else if (pistonhighrt && pistonhighlft)
         {
            ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.RIGHTHIGHPIS.getName())).setValue(false
                  ^ invtOutputs);
            ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.LEFTHIGHPIS.getName())).setValue(false
                  ^ invtOutputs);
            pistonhighrt = false ^ invtOutputs;
            pistonhighlft = false ^ invtOutputs;
         }
      }
      /*
       * Runs the winch
       */
      if (winchInput)
      {
         ((AnalogInput) Core.getOutputManager().getOutput(WSOutputs.RIGHTWINCH.getName())).setValue(.3);
         ((AnalogInput) Core.getOutputManager().getOutput(WSOutputs.LEFTWINCH.getName())).setValue(.3);
      }
      else{
         ((AnalogInput) Core.getOutputManager().getOutput(WSOutputs.RIGHTWINCH.getName())).setValue(0.0);
         ((AnalogInput) Core.getOutputManager().getOutput(WSOutputs.LEFTWINCH.getName())).setValue(0.0);
      }

   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

}
