package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.I2CInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.SwerveInputs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IMUTest implements Subsystem{
	
	private byte[] HeadingBytes = new byte [2];
	private double CompassHeading;
	
	public IMUTest()
	{
		
	}

	@Override
	public void inputUpdate(Input p_source) {
		// TODO Auto-generated method stub
		if (p_source.getName().equals(SwerveInputs.IMU.getName()))
	      {
			HeadingBytes = ((I2CInput) p_source).getValue();
	      }
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		 Core.getInputManager().getInput(SwerveInputs.IMU.getName()).addInputListener(this);
	}

	@Override
	public void selfTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		CompassHeading = (double)((HeadingBytes[0] << 8) + HeadingBytes[1]);
		SmartDashboard.putNumber("IMU Heading", CompassHeading);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "IMU Test";
	}

}
