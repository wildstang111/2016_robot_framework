package org.wildstang.yearly.auto.programs;


import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.steps.drivebase.StepVisionAdjustment;
import org.wildstang.yearly.auto.steps.intake.StepSetIntakeState;

public class VisionTest extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new StepSetIntakeState(true));
      addStep(new StepVisionAdjustment());
      addStep(new StepSetIntakeState(false));
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Test Vision Alignment";
   }

}
