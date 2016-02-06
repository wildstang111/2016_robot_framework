package org.wildstang.yearly.auto.steps.intake;

import org.wildstang.framework.auto.steps.AutoStep;

public class StepSetIntakeState extends AutoStep
{
   private boolean deployed;

   public StepSetIntakeState(boolean setState)
   {
      this.deployed = setState;
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
      return "Intake deployed: " + deployed;
   }

}
