/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.yearly.pid.outputs;

import org.wildstang.framework.core.Core;
import org.wildstang.fw.pid.output.IPidOutput;
import org.wildstang.yearly.subsystems.DriveBase;
import org.wildstang.yearly.subsystems.WSSubsystems;

/**
 *
 * @author Nathan
 */
public class DriveBaseSpeedPidOutput implements IPidOutput {

	public DriveBaseSpeedPidOutput() {
		// Nothing to do here
	}

	public void pidWrite(double output) {
		((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).setPidSpeedValue(output);
	}
}
