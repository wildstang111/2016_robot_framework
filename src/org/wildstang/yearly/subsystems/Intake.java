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
	private boolean frontRoller;

   @Override
   public void inputUpdate(Input source)
   {
      // TODO Auto-generated method stub
      
      // does something with Inputs and variables
      
      // setting buttonPress to DRV_BUTTON_1
	   if (source.getName().equals(WSInputs.DRV_BUTTON_2.getName()))
	   	{
	   		buttonPress = ((DigitalInput)source).getValue();
	   	}
	   
	   // setting digitalIO_0 to DIO_0
/*	   else if (source.getName().equals(WSInputs.DIO_0_INTAKE_SENSOR.getName()))
      {
	      sensorReading = ((DigitalInput)source).getValue();
	      
	      if (frontRoller = true)
	      {
	         buttonPress = true;
	      }
	      else
	      {
	         buttonPress = false;
	      }
      }
	   else if (source.getName().equals(WSInputs.DRV_BUTTON_1.getName()))
      {
	      frontRoller = ((DigitalInput)source).getValue();
      }
*/
   }

   @Override
   public void init()
   {
      // TODO Auto-generated method stub
     
      // asking for below Inputs
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_1.getName()).addInputListener(this);
//	   Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName()).addInputListener(this);

	   Core.getInputManager().getInput(WSInputs.DIO_0_INTAKE_SENSOR.getName()).addInputListener(this);
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
      
      // tells status of booleans buttonPress and digitalIO_0
	   System.out.println("the boolean, buttonPress, is " + buttonPress);
	   System.out.println("the boolean, sensorReading, is " + sensorReading);
	   
	   // booleans buttonPress and digitalIO_0 control LED_0 and LED_1 respectively
	   ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.DIO_LED_0.getName())).setValue(buttonPress);
	   ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.SENSOR_LED_1.getName())).setValue(sensorReading);
	   ((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER_LED_2.getName())).setValue(frontRoller);
	   //((DigitalOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER.getName())).setValue(frontRoller);
   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

}
