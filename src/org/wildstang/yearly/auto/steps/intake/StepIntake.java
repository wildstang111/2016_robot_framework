package org.wildstang.yearly.auto.steps.intake;

import org.wildstang.framework.auto.steps.AutoStep;

public class StepIntake extends AutoStep
{
   private int speed;

   public StepIntake(int runningSpeed)
   {
      this.speed = runningSpeed;
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
      return "Running intake at: " + speed;
   }

}
