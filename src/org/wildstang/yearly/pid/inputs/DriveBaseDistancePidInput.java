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
public class DriveBaseDistancePidInput implements IPidInput
{

   public DriveBaseDistancePidInput()
   {
      // Nothing to do here
   }

   @Override
   public double pidRead()
   {
      double right_encoder_value;
      right_encoder_value = ((DriveBase) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).getRightDistance();
      SmartDashboard.putNumber("Distance: ", right_encoder_value);
      return right_encoder_value;
   }
}
