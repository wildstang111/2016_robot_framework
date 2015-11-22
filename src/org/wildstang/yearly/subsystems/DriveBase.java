package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.outputs.AnalogOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.TestOutputs;

public class DriveBase implements Subsystem
{
   // WsVictor victor1;
   // WsVictor victor2; NO - new framework doesn't require this
   double throttle;


   public DriveBase()
   {

   }

   @Override
   public void inputUpdate(Input source)
   {
//      if (source.getName() == WSInputs.DRV_BUTTON_1.getName())
//      {
//
//      }
//      if (source.getName() == WSInputs.DRV_THROTTLE.getName())
//      {
//         throttle = ((AnalogInput) source).getValue();
//      }
   }

   @Override
   public void init()
   {
//      Core.getInputManager().getInput(WSInputs.DRV_HEADING.getName()).addInputListener(this);
//      Core.getInputManager().getInput(WSInputs.DRV_THROTTLE.getName()).addInputListener(this);
   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update()
   {
//      ((AnalogOutput) Core.getOutputManager().getOutput(TestOutputs.VICTOR.getName())).setValue(throttle);

   }

   @Override
   public String getName()
   {
      return "Drive Base";
   }

}
