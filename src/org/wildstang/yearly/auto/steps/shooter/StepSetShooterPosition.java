package org.wildstang.yearly.auto.steps.shooter;

import org.wildstang.framework.auto.steps.AutoStep;

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

   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Shooter deployed: " + state;
   }

}
