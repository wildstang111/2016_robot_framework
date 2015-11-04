package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.WSInputs;

public class DriveBase implements Subsystem{
	WsVictor victor1;
	WsVictor victor2;
	Double throttle;
	
	public DriveBase(String name) {

	      
	
	}
	
	
	@Override
	public void inputUpdate(Input source) {
		// TODO Auto-generated method stub
		if (source.getName() == WSInputs.DRV_BUTTON_1.getName()) {
			
		}
		if (source.getName() == WSInputs.DRV_THROTTLE.getName()) {
			throttle = ((AnalogInput)source).getValue();
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		Core.getInputManager().getInput(WSInputs.DRV_HEADING.getName()).addInputListener(this);
	    Core.getInputManager().getInput(WSInputs.DRV_THROTTLE.getName()).addInputListener(this);
		victor1 = new WsVictor("Test", 0, 0.0);
	}

	@Override
	public void selfTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		victor1.setValue(throttle);
		victor1.sendDataToOutput();
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
