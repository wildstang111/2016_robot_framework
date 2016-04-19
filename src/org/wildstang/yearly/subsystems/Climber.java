package org.wildstang.yearly.subsystems;

import org.wildstang.framework.config.Config;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;
import org.wildstang.yearly.robot.WSSubsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber implements Subsystem
{
   /*
    * Climber Robot Class Authors: Wallace Butler and Lucas Papaioannou
    */
   private boolean armsDeploying = false;
   private boolean armsDeployed = false;
   private boolean hooksDeployed = false;
   private boolean deployStarted = false;

   private boolean winchMoved = false;

   private int startDelay = 0;
   private int stopDelay = 0;

   private boolean brakeEngaged = true;

   private double joystickWinchSpeed = 0.0;
   private double winchSpeed = 0.0;
   
   private boolean override = false;
   
   private double armHelpSpeed;
   private double armHelpRunTime;
   private static final String armsUpOutSpeed = ".arm_up_out_speed";
   private static final String armsUpRunTime = ".arm_up_run_time";
   private static final double ARMOUTSPEED_DEFAULT = .4;
   private static final double ARMOUTTIME_DEFAULT = 1000.0;
   private double winchEndTime;
   private boolean winchLimit;

//   private WsSolenoid brake;
   private WsSolenoid leftBrake;
   private WsSolenoid rightBrake;
   private WsDoubleSolenoid hooks;
//   private WsSolenoid lowerArm;
//   private WsSolenoid upperArm;
   private WsSolenoid armSolenoid;
   private WsVictor leftWinch;
   private WsVictor rightWinch;

   @Override
   public void inputUpdate(Input source)
   {
      if (source.getName().equals(WSInputs.MAN_BUTTON_1.getName()))
      {
         if (((DigitalInput) source).getValue())
         {
            armsDeploying = true;
            winchEndTime = Timer.getFPGATimestamp() + armHelpRunTime;
         }
      }
      else if (source.getName().equals(WSInputs.MAN_RIGHT_JOYSTICK_Y.getName()))
      {
         joystickWinchSpeed = ((AnalogInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_2.getName()))
      {
         if (((DigitalInput) source).getValue())
         {
            hooksDeployed = !hooksDeployed;
         }

      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_9.getName()))
      {
//       if (((DigitalInput) source).getValue())
//       {
//          override = true;
//       }
      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_10.getName()))
      {
         winchLimit = ((DigitalInput) source).getValue();
      }
   }

   @Override
   public void init()
   {
      Core.getInputManager().getInput(WSInputs.MAN_RIGHT_JOYSTICK_Y.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_1.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_9.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_10.getName()).addInputListener(this);
      //Core.getInputManager().getInput(WSInputs.RIGHT_ARM_TOUCHING.getName()).addInputListener(this);
      //Core.getInputManager().getInput(WSInputs.LEFT_ARM_TOUCHING.getName()).addInputListener(this);
      leftBrake = ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.LEFT_BRAKE.getName()));
      rightBrake = ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.RIGHT_BRAKE.getName()));
      hooks = (WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.HOOK_EXTENSION.getName());
      armSolenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.ARMS.getName());
      leftWinch = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.WINCH_LEFT.getName());
      rightWinch = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.WINCH_RIGHT.getName());
      
      armHelpSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + armsUpOutSpeed, ARMOUTSPEED_DEFAULT);

      armHelpRunTime = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + armsUpRunTime, ARMOUTTIME_DEFAULT);
   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update()
   {
      if(winchLimit)
      {
         joystickWinchSpeed /= 2;
      }
      
      
      if (armsDeploying && !armsDeployed)
      {
         // Flag that we are deploying the arms (also used to control the arm pistons)
         armsDeploying = true;
         
         // Set the wind-out winch speed
         winchSpeed = -armHelpSpeed;
         
         // Make sure the intake is out of the way
         if (!deployStarted)
         {
            // Flag to not repeatedly call this
            deployStarted = true;
            protectIntake();
         }

         if (Timer.getFPGATimestamp() >= winchEndTime)
         {
            winchSpeed = 0.0;
            armsDeployed = true;
         }
         
         // E-stop arms!
         if ((joystickWinchSpeed <= -0.1) || (joystickWinchSpeed >= 0.1))
         {
            winchSpeed = 0.0;
         }
      }
      
      
      if (armsDeployed)
      {
         if ((joystickWinchSpeed <= -0.1) || (joystickWinchSpeed >= 0.1))
         {
            // Steve: Do we need this state check?  I was going somewhere with this, but can't remember now...
            if (!winchMoved)
            {
               winchMoved = true;
               
               // If we have started to move, remove air from the arm pistons
               armsDeploying = false;
            }

            winchSpeed = joystickWinchSpeed;
         }

         // Safety - don't pull down too far
         // TODO: Need switches/limit sensor
//         if (upperArmLimit || lowerArmLimit)
//         {
//            winchSpeed = 0;
//         }
         
      }

      // Set if the brake should be engaged.  Note this is the reverse of what you expect, due to the wiring of the solenoid
      if (winchSpeed == 0)
      {
         brakeEngaged = false;
      }
      else
      {
         brakeEngaged = true;
      }

      // Set the output values
      armSolenoid.setValue(armsDeploying);
      if (!hooksDeployed)
      {
         hooks.setValue(WsDoubleSolenoidState.REVERSE.ordinal());
      }
      else
      {
         hooks.setValue(WsDoubleSolenoidState.FORWARD.ordinal());
      }

      leftWinch.setValue(winchSpeed);
      rightWinch.setValue(winchSpeed);
      rightBrake.setValue(brakeEngaged);
      leftBrake.setValue(brakeEngaged);

      
      SmartDashboard.putBoolean("Arms deploying", armsDeploying);
      SmartDashboard.putBoolean("Arms deployed", armsDeployed);
      SmartDashboard.putBoolean("hooks deployed", hooksDeployed);
      SmartDashboard.putNumber("Winch Speed", winchSpeed);
      SmartDashboard.putBoolean("Override", override);
      SmartDashboard.putBoolean("brakeEngaged", brakeEngaged);
      SmartDashboard.putBoolean("Arms", armSolenoid.getValue());
      SmartDashboard.putBoolean("Left Brake", leftBrake.getValue());
      SmartDashboard.putBoolean("Right Brake", rightBrake.getValue());
   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return "Climber";
   }
   
   public void notifyConfigChange(Config p_newConfig)
   {
      armHelpSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + armsUpOutSpeed, ARMOUTSPEED_DEFAULT);

      armHelpRunTime = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + armsUpRunTime, ARMOUTTIME_DEFAULT);

   }
   
   public void protectIntake()
   {
      if (((Intake) Core.getSubsystemManager().getSubsystem(WSSubsystems.INTAKE.getName())).isDeployed() != true)
      {
         ((DigitalInput) Core.getInputManager().getInput(WSInputs.MAN_BUTTON_8.getName())).setValue(true);
      }
   }
   
   public void resetIntakeToggle()
   {
      ((DigitalInput)Core.getInputManager().getInput(WSInputs.MAN_BUTTON_8.getName())).setValue(false);
   }

}
