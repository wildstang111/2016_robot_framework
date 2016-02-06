package org.wildstang.yearly.auto.steps.shooter;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

public class StepShoot extends AutoStep
{
   public StepShoot()
   {

   }

   @Override
   public void initialize()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub
      if(((Shooter)Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName())).doesSpeedMatch())
      {
      ((DigitalInput)Core.getInputManager().getInput(WSInputs.MAN_BUTTON_7.getName())).setValue(true);
      ((DigitalInput)Core.getInputManager().getInput(WSInputs.MAN_BUTTON_7.getName())).setValue(false);
      }
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Shot";
   }

}
