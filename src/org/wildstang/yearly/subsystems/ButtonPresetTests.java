package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.outputs.AnalogOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

public class ButtonPresetTests implements Subsystem
{
  private double m_currentHeading;
  private double m_currentThrottle;
  private double leftSpeed;
  private double rightSpeed;
  private double speedMod = 1.0;

  /* Constructor should not take args to insure that it can be instantiated via reflection. */
   public ButtonPresetTests()
   {

   }

   @Override
   public void inputUpdate(Input source)
   {
	   
	   	if (source.getName().equals(WSInputs.DRV_HEADING.getName()))
   		{
	   		m_currentHeading = ((AnalogInput)source).getValue();
   		}
	   	else if (source.getName().equals(WSInputs.DRV_THROTTLE.getName()))
	   	{
	   		m_currentThrottle = ((AnalogInput)source).getValue();
	   	}
		else if (source.getName().equals(WSInputs.DRV_BUTTON_1.getName()))
	   	{
	   		speedMod = 1.0;
	   	}
	   	else if (source.getName().equals(WSInputs.DRV_BUTTON_2.getName()))
	   	{
	   		speedMod = .75;
	   	}
	   	else if (source.getName().equals(WSInputs.DRV_BUTTON_3.getName()))
	   	{
	   		speedMod = .5;
	   	}
	   	else if (source.getName().equals(WSInputs.DRV_BUTTON_4.getName()))
	   	{
	   		speedMod = .25;
	   	}
	   	else if (source.getName().equals(WSInputs.DRV_BUTTON_5.getName()))
	   	{
	   		speedMod += 0.05;
	   	}
	   	else if (source.getName().equals(WSInputs.DRV_BUTTON_6.getName()))
	   	{
	   		speedMod -= 0.05;
	   	}

   }

   @Override
   public void init()
   {
      Core.getInputManager().getInput(WSInputs.DRV_HEADING.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_THROTTLE.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_1.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_3.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_4.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_5.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_6.getName()).addInputListener(this);
   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update()
   {
	   /* Quick and dirty 6 wheel drive control. */
	   if (m_currentThrottle > -0.1 && m_currentThrottle < 0.1)
	   {
		   /* Zero Point turn */
		   if (m_currentHeading > 0.1)
		   {
			   leftSpeed = Math.abs(m_currentHeading);
			   rightSpeed = -1 * Math.abs(m_currentHeading);
		   }
		   else if (m_currentHeading < -0.1)
		   {
			   rightSpeed = -1 * Math.abs(m_currentHeading); 
			   leftSpeed = Math.abs(m_currentHeading);
		   }
		   else
		   {
			   rightSpeed = 0;
			   leftSpeed = 0;
		   }
	   }
	   else if (m_currentHeading > 0.1)
	   {
		   leftSpeed = m_currentThrottle * (1 - Math.abs(m_currentHeading));
		   rightSpeed = m_currentThrottle;
	   }
	   else if (m_currentHeading < -0.1)
	   {
		   rightSpeed = m_currentThrottle * (1 - Math.abs(m_currentHeading)); 
		   leftSpeed = m_currentThrottle;
	   }
	   else
	   {
		   leftSpeed = m_currentThrottle;
   	   rightSpeed = m_currentThrottle;
	   }
	   	   
      ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_LEFT.getName())).setValue(leftSpeed * speedMod);
//      ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.REAR_LEFT.getName())).setValue(leftSpeed * speedMod);
      ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_RIGHT.getName())).setValue(rightSpeed * speedMod);
//      ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.REAR_RIGHT.getName())).setValue(rightSpeed * speedMod);
   }

   @Override
   public String getName()
   {
      return "Drive Base";
   }

}
