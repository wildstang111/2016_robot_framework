package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.outputs.DiscreteOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsRelay;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

public class HardwareTest implements Subsystem
{
   boolean m_limitSwitch;

   @Override
   public void inputUpdate(Input source)
   {
      if (source.getName().equals(WSInputs.LIMIT_SWITCH.getName()))
      {
         m_limitSwitch = ((DigitalInput)source).getValue();
      }
   }

   @Override
   public void init()
   {
      Core.getInputManager().getInput(WSInputs.LIMIT_SWITCH.getName()).addInputListener(this);
   }

   @Override
   public void selfTest()
   {
   }

   @Override
   public void update()
   {
      if (m_limitSwitch)
      {
         ((DiscreteOutput)Core.getOutputManager().getOutput(WSOutputs.SPIKE.getName())).setValue(WsRelay.RELAY_ON);
      }
      else
      {
         ((DiscreteOutput)Core.getOutputManager().getOutput(WSOutputs.SPIKE.getName())).setValue(WsRelay.RELAY_ON);
      }
   }

   @Override
   public String getName()
   {
      return "Hardware Test";
   }

}
