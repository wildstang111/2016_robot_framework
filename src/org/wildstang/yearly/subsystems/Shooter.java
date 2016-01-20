package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.WSInputs;

import edu.wpi.first.wpilibj.CANTalon;

public class Shooter implements Subsystem {
	private boolean flyWheelToggle = false;
	private double talonTemp;
	CANTalon flyWheel;
	
	@Override
	public void inputUpdate(Input source) {
		// TODO Auto-generated method stub
		if (source.getName().equals(WSInputs.DRV_BUTTON_4.getName())) {
			//driver button 4 (triangle)
			if (flyWheelToggle == true) {
				flyWheelToggle = false;
			} else if (flyWheelToggle == false) {
				flyWheelToggle = true;
			} else {
				flyWheelToggle = true;
			}
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		Core.getInputManager().getInput(WSInputs.DRV_BUTTON_4.getName()).addInputListener(this);
		flyWheel = new CANTalon(1);
//		flyWheel.changeControlMode(ControlMode.Voltage);
//		flyWheel.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		flyWheel.reverseSensor(false);
		flyWheel.reverseOutput(false);
//		flyWheel.setF(0.5996);
//		flyWheel.setP(0.1);
		flyWheel.enableControl();
//		 flyWheel.setVoltageRampRate(rampRate);
		
	}

	@Override
	public void selfTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (flyWheelToggle == true) {
			flyWheel.set(200);
		}
		else
		{
			flyWheel.set(0.0);
		}
		
	}

	private void getAnalogInVelocity() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
