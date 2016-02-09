package org.wildstang.yearly.auto.steps.shooter;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.yearly.robot.WSInputs;

public class StepResetShotToggle extends AutoStep
{
   public StepResetShotToggle()
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
      ((DigitalInput)Core.getInputManager().getInput(WSInputs.MAN_BUTTON_7.getName())).setValue(false);
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Shot reset";
   }

}
