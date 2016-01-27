package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.AutoParallelStepGroup;
import org.wildstang.framework.auto.steps.AutoSerialStepGroup;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveDistanceAtSpeed;
import org.wildstang.yearly.auto.steps.intake.StepSetIntakeState;
import org.wildstang.yearly.auto.steps.shooter.StepShoot;

public class OneBallMoatRampart extends AutoProgram
{
   private double speed;
   private int defensePosition;

   @Override
   protected void defineSteps()
   {
      // TODO Auto-generated method stub
      AutoParallelStepGroup crossDefense = new AutoParallelStepGroup();
      AutoSerialStepGroup crossSeries = new AutoSerialStepGroup();
      // Drive to low bar and cross low bar
      crossSeries.addStep(new StepDriveDistanceAtSpeed(52.5, 1, true));
      crossSeries.addStep(new StepDriveDistanceAtSpeed(80.5, 1, true));
      crossDefense.addStep(crossSeries);
      // Wait 1 second before deploying intake
      crossDefense.addStep(new AutoStepDelay(1000));
      crossDefense.addStep(new StepSetIntakeState(true));
      addStep(crossDefense);

      AutoParallelStepGroup findGoal = new AutoParallelStepGroup();
      AutoSerialStepGroup gotoGoal = new AutoSerialStepGroup();
      // add in 4 different cases for driving waypoints based on defense
      // position

      addStep(new StepShoot());

   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "One Ball Moat or Rampart";
   }

}
