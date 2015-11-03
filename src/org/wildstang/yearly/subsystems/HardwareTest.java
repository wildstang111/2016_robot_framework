package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
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
//      if (source.getName().equals(WSInputs.LIMIT_SWITCH.getName()))
//      {
//         m_limitSwitch = ((DigitalInput)source).getValue();
//      }
    /*  else */if (source.getName().equals(WSInputs.DRV_BUTTON_1.getName()))
      {
         printButtonState(source, 1);
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_2.getName()))
      {
         printButtonState(source, 3);
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_3.getName()))
      {
         printButtonState(source, 3);
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_4.getName()))
      {
         printButtonState(source, 4);
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_5.getName()))
      {
         printButtonState(source, 5);
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_6.getName()))
      {
         printButtonState(source, 6);
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_7.getName()))
      {
         printButtonState(source, 7);
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_8.getName()))
      {
         printButtonState(source, 8);
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_9.getName()))
      {
         printButtonState(source, 9);
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_10.getName()))
      {
         printButtonState(source, 10);
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_11.getName()))
      {
         printButtonState(source, 11);
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_12.getName()))
      {
         printButtonState(source, 12);
      }
      else if (source.getName().equals(WSInputs.DRV_HEADING.getName()))
      {
         printJoystickState(source);
      }
      else if (source.getName().equals(WSInputs.DRV_THROTTLE.getName()))
      {
         printJoystickState(source);
      }
      else if (source.getName().equals(WSInputs.DRV_LEFT_X.getName()))
      {
         printJoystickState(source);
      }
      else if (source.getName().equals(WSInputs.DRV_RIGHT_Y.getName()))
      {
         printJoystickState(source);
      }
//      else if (source.getName().equals(WSInputs.DRV_DPAD_X.getName()))
//      {
//      }
//      else if (source.getName().equals(WSInputs.DRV_DPAD_Y.getName()))
//      {
//      }
   }

   private void printButtonState(Input source, int button)
   {
      if (((DigitalInput)source).getValue())
      {
         System.out.println("Driver button " + button + " pressed");
      }
      else
      {
         System.out.println("Driver button " + button + " released");
      }
   }

   private void printJoystickState(Input source)
   {
         System.out.println(source.getName() + " value: " + ((AnalogInput)source).getValue());
   }

   @Override
   public void init()
   {
  //    Core.getInputManager().getInput(WSInputs.LIMIT_SWITCH.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_1.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_3.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_4.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_5.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_6.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_7.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_8.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_9.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_10.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_11.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_12.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_HEADING.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_THROTTLE.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_LEFT_X.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_RIGHT_Y.getName()).addInputListener(this);
//      Core.getInputManager().getInput(WSInputs.DRV_DPAD_X.getName()).addInputListener(this);
//      Core.getInputManager().getInput(WSInputs.DRV_DPAD_Y.getName()).addInputListener(this);
      
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
         ((DiscreteOutput)Core.getOutputManager().getOutput(WSOutputs.SPIKE.getName())).setValue(WsRelay.RELAY_OFF);
      }
   }

   @Override
   public String getName()
   {
      return "Hardware Test";
   }

}
