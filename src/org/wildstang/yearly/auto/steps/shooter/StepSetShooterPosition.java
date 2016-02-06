package org.wildstang.yearly.auto.steps.shooter;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

public class StepSetShooterPosition extends AutoStep
{
   private boolean state;

   public StepSetShooterPosition(boolean position)
   {
      this.state = position;
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
      if(((Shooter)Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName())).hoodPos() != state)
      {
      ((DigitalInput)Core.getInputManager().getInput(WSInputs.MAN_BUTTON_5.getName())).setValue(true);
      ((DigitalInput)Core.getInputManager().getInput(WSInputs.MAN_BUTTON_5.getName())).setValue(false);
      }
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Shooter deployed: " + state;
   }

}
