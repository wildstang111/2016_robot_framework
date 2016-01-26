package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.AutoParallelStepGroup;
import org.wildstang.framework.auto.steps.AutoSerialStepGroup;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveDistanceAtSpeed;
import org.wildstang.yearly.auto.steps.drivebase.StepQuickTurn;
import org.wildstang.yearly.auto.steps.intake.StepIntake;
import org.wildstang.yearly.auto.steps.intake.StepSetIntakeState;
import org.wildstang.yearly.auto.steps.shooter.StepRunFlywheel;
import org.wildstang.yearly.auto.steps.shooter.StepShoot;

public class TwoBall extends AutoProgram
{
   private double speed;

   @Override
   protected void defineSteps()
   {
      // TODO Auto-generated method stub
      AutoParallelStepGroup beginAuto = new AutoParallelStepGroup("Start shooter and go to goal");
      AutoSerialStepGroup gotoGoal = new AutoSerialStepGroup("Go to goal");
      // Start shooter to get to speed
      beginAuto.addStep(new StepRunFlywheel(speed));
      // Drive to point in line with goal
      gotoGoal.addStep(new StepDriveDistanceAtSpeed(-71.25, 1, true));
      // Turn to face goal
      gotoGoal.addStep(new StepQuickTurn(90));
      beginAuto.addStep(gotoGoal);
      addStep(beginAuto);

      // Shoot
      addStep(new StepShoot());

      AutoParallelStepGroup leaveCourtyard = new AutoParallelStepGroup("Stop flywheel and go to low bar");
      AutoSerialStepGroup gotoLowBar = new AutoSerialStepGroup("Go to low bar");
      // Stop flywheel
      leaveCourtyard.addStep(new StepRunFlywheel(0));
      // Drive to point in line with low bar
      gotoLowBar.addStep(new StepDriveDistanceAtSpeed(-19, 1, true));
      // Turn to face low bar
      gotoLowBar.addStep(new StepQuickTurn(-60));
      // Drive to low bar
      gotoLowBar.addStep(new StepDriveDistanceAtSpeed(-108, 1, true));
      leaveCourtyard.addStep(gotoLowBar);
      addStep(leaveCourtyard);

      // Cross low bar
      addStep(new StepDriveDistanceAtSpeed(-80.5, 1, true));
      // Turn to face ball
      addStep(new StepQuickTurn(-21));

      AutoParallelStepGroup grabBall = new AutoParallelStepGroup("Run intake and go to/return from ball");
      AutoSerialStepGroup gotoBall = new AutoSerialStepGroup("Go to ball");
      // Intake ball
      grabBall.addStep(new StepIntake(true));
      // Deploy intake
      gotoBall.addStep(new StepSetIntakeState(true));
      // Drive to ball
      gotoBall.addStep(new StepDriveDistanceAtSpeed(-56, 1, true));
      // Wait while ball is collected
      gotoBall.addStep(new AutoStepDelay(1000));
      // Retract intake
      gotoBall.addStep(new StepSetIntakeState(false));
      grabBall.addStep(gotoBall);
      addStep(grabBall);

      // Stop intake
      addStep(new StepIntake(false));
      // Drive to low bar
      addStep(new StepDriveDistanceAtSpeed(56, 1, true));
      // Turn to face low bar
      addStep(new StepQuickTurn(21));

      AutoParallelStepGroup shootTwo = new AutoParallelStepGroup("Return to shooting position and shoot");
      AutoSerialStepGroup gotoGoalTwo = new AutoSerialStepGroup("Go to goal again");
      // Start shooter to get to speed
      shootTwo.addStep(new StepRunFlywheel(speed));
      // Cross low bar
      gotoGoalTwo.addStep(new StepDriveDistanceAtSpeed(80.5, 1, true));
      // Drive to point in line with goal
      gotoGoalTwo.addStep(new StepDriveDistanceAtSpeed(108, 1, true));
      // Turn to face goal
      gotoGoalTwo.addStep(new StepQuickTurn(60));
      shootTwo.addStep(gotoGoalTwo);
      addStep(shootTwo);

      // Shoot
      addStep(new StepShoot());
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Two Ball Auto";
   }

}
