package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.AutoParallelStepGroup;
import org.wildstang.framework.auto.steps.AutoSerialStepGroup;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveDistanceAtSpeed;
import org.wildstang.yearly.auto.steps.drivebase.StepQuickTurn;
import org.wildstang.yearly.auto.steps.intake.StepIntake;
import org.wildstang.yearly.auto.steps.intake.StepSetIntakeState;
import org.wildstang.yearly.auto.steps.shooter.StepRunFlywheel;
import org.wildstang.yearly.auto.steps.shooter.StepShoot;

public class HarpoonAuto extends AutoProgram
{
   private double speed;

   @Override
   protected void defineSteps()
   {
      // Shove balls out of the way on our crusade to the low bar
      AutoParallelStepGroup driveToEnd = new AutoParallelStepGroup();
      AutoSerialStepGroup deployAndDrive = new AutoSerialStepGroup();
      deployAndDrive.addStep(new StepSetIntakeState(true));
      deployAndDrive.addStep(new StepDriveDistanceAtSpeed(276, 1, false));
      driveToEnd.addStep(new StepIntake(-1));
      driveToEnd.addStep(deployAndDrive);
      addStep(driveToEnd);

      AutoSerialStepGroup crossTheRoad = new AutoSerialStepGroup();
      crossTheRoad.addStep(new StepSetIntakeState(false));
      crossTheRoad.addStep(new StepQuickTurn(90));
      crossTheRoad.addStep(new StepDriveDistanceAtSpeed(32, 1, false));
      crossTheRoad.addStep(new StepSetIntakeState(true));
      crossTheRoad.addStep(new StepDriveDistanceAtSpeed(80.5, 1, true));
      addStep(crossTheRoad);

      AutoParallelStepGroup Score = new AutoParallelStepGroup();
      AutoSerialStepGroup gotoGoal = new AutoSerialStepGroup();
      Score.addStep(new StepRunFlywheel(speed));
      gotoGoal.addStep(new StepDriveDistanceAtSpeed(108, 1, true));
      gotoGoal.addStep(new StepQuickTurn(60));
      Score.addStep(gotoGoal);
      addStep(Score);

      addStep(new StepShoot());
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Neutralizing 2 balls";
   }

}
