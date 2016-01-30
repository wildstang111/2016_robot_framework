package org.wildstang.yearly.auto.steps.shooter;

import org.wildstang.framework.auto.steps.AutoStep;

public class StepRunFlywheel extends AutoStep
{
   private double speed;

   public StepRunFlywheel(double speed)
   {
      this.speed = speed;
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
      return "Running flywheel at " + speed;
   }

}
