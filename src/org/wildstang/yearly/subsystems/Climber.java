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

  
  
  
   @Override
   public void inputUpdate(Input source)
   {
      if (source.getName().equals(WSInputs.LIFT_BUTTON.getName()))
      {
         liftButton = ((DigitalInput)source).getValue();
      }

    


   }

   @Override
   public void init()
   {
      Core.getInputManager().getInput(WSInputs.LIFT_BUTTON.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName()).addInputListener(this);
  
     

   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update()
   {
      System.out.println("Lift: " + liftButton);
      //System.out.println("Magnet: " + magnetdet);
      ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.RIGHTLOWPIS.getName())).setValue(liftButton);
      ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.RIGHTHIGHPIS.getName())).setValue(liftButton);
      ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.LEFTLOWPIS.getName())).setValue(liftButton);
      ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.LEFTHIGHPIS.getName())).setValue(liftButton);
   
      

   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

}
