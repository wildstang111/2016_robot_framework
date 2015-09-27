package org.wildstang.yearly.pid.inputs;

import org.wildstang.framework.core.Core;
import org.wildstang.fw.pid.input.IPidInput;
import org.wildstang.yearly.subsystems.DriveBase;
import org.wildstang.yearly.subsystems.WSSubsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class DriveBaseHeadingPidInput implements IPidInput
{

   public DriveBaseHeadingPidInput()
   {
      // Nothing to do here
   }

   @Override
   public double pidRead()
   {
      double gyro_angle;
      gyro_angle = ((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).getGyroAngle();
      SmartDashboard.putNumber("Gyro angle: ", gyro_angle);
      return gyro_angle;
   }
}
