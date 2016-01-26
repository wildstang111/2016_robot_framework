package org.wildstang.yearly.subsystems;

//expand this and edit if trouble with Ws
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.outputs.DigitalOutput;
import org.wildstang.framework.io.outputs.AnalogOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

public class Intake implements Subsystem
{
   // add variables here
   private boolean buttonPress;
   private boolean sensorReading;
   private boolean buttonPress2;
   private boolean rollerMoving;
   private boolean pnumaticGo;
   private boolean buttonPress3;

   @Override
   public void inputUpdate(Input source)
   {
      // TODO Auto-generated method stub

      // does something with Inputs and variables

      // setting buttonPress to DRV_BUTTON_1
      if (source.getName().equals(WSInputs.DRV_BUTTON_1.getName()))
      {
         buttonPress = ((DigitalInput) source).getValue();
      }

      // setting buttonPress2 to DRV_BUTTON_2
      else if (source.getName().equals(WSInputs.DRV_BUTTON_2.getName()))
      {
         buttonPress2 = ((DigitalInput) source).getValue();
      }

      // setting sensorReading to DIO_0_INTAKE_SENSOR
      else if (source.getName().equals(WSInputs.INTAKE_BOLDER_SENSOR.getName()))
      {
         sensorReading = ((DigitalInput) source).getValue();
      }

      // setting buttonPress3 to DRV_BUTTON_3
      else if (source.getName().equals(WSInputs.DRV_BUTTON_3.getName()))
      {
         buttonPress3 = ((DigitalInput) source).getValue();
      }

   }

   @Override
   public void init()
   {
      // TODO Auto-generated method stub

      // asking for below Inputs
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_1.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_3.getName()).addInputListener(this);

      Core.getInputManager().getInput(WSInputs.INTAKE_BOLDER_SENSOR.getName()).addInputListener(this);
   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub

      // does something with variables and Outputs

      // tells status of buttonPress, digitalIO_0, sensorReading, and
      // rollerMoving
      System.out.println("buttonPress=" + buttonPress + " buttonPress2="
            + buttonPress2 + " sensorReading=" + sensorReading
            + " rollerMoving=" + rollerMoving);

      // toggles rollerMoving to buttonPress
      if (buttonPress == true)
      {
         rollerMoving = true;
      }
      // toggles pistonGo to buttonPress3
      if (buttonPress3 == true)
      {
         pnumaticGo = true;
      }

      // if the sensor is triggered, the roller will not move unless button2 is
      // pressed
      if (buttonPress2 == true)
      {
         rollerMoving = true;
         sensorReading = false;
      }

      else if (sensorReading == true)
      {
         rollerMoving = false;
      }

      if (rollerMoving == true)
      {
         ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER.getName())).setValue(0.75);
      }

      // buttonPress controls DIO_LED_0 etc.
      ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.DIO_LED_0.getName())).setValue(buttonPress);
      ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.SENSOR_LED_1.getName())).setValue(sensorReading);
      ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER_LED_2.getName())).setValue(rollerMoving);
   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

}
