package org.wildstang.yearly.subsystems;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.InputManager;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.SwerveInputs;
import org.wildstang.yearly.robot.WSOutputs;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.swerve.CrabDriveMode;
import org.wildstang.yearly.subsystems.swerve.SwerveBaseState;
import org.wildstang.yearly.subsystems.swerve.SwerveDriveMode;

import edu.wpi.first.wpilibj.DriverStation;

public class SwerveDrive implements Subsystem
{
   private static final int CRAB = 0;
   private static final int SWERVE = 1;
   
   private double m_joystickRotation = 0.0;
   private double m_headingX = 0.0;
   private double m_headingY = 0.0;

   private boolean m_hallEffect = false;
   private double m_currentEncoder = 0.0;
   
   private int m_prevHeadingAngle = 0;
   private int m_mode = CRAB;
   private boolean m_recalcMode = false;

   // Drive motor outputs
   private WsVictor m_frontLeftDrive = null;
   private WsVictor m_frontRightDrive = null;
   private WsVictor m_rearLeftDrive = null;
   private WsVictor m_rearRightDrive = null;

   private WsVictor m_frontLeftRotate = null;
   private WsVictor m_frontRightRotate = null;
   private WsVictor m_rearLeftRotate = null;
   private WsVictor m_rearRightRotate = null;

   private CrabDriveMode m_crabDriveMode = new CrabDriveMode();
   private SwerveDriveMode m_swerveDriveMode = new SwerveDriveMode();
   private SwerveBaseState m_prevState = null;
   
   private static final DecimalFormat s_format = new DecimalFormat("#.##");
   
   private static Logger s_log = Logger.getLogger(SwerveDrive.class.getName());
   private static final String s_className = "SwerveDrive";

   public SwerveDrive()
   {
   }
   
   @Override
   public void inputUpdate(Input p_source)
   {
      if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_1.getName()))
      {
         // TODO: Assume button 1 changes drive mode
         m_recalcMode = true;
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_2.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_3.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_4.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_5.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_6.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_7.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_8.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_9.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_10.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_11.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_BUTTON_12.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_HEADING_X.getName()))
      {
         m_headingX = ((AnalogInput) p_source).getValue();
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_HEADING_Y.getName()))
      {
         m_headingY = ((AnalogInput) p_source).getValue();
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_ROTATION.getName()))
      {
         m_joystickRotation = ((AnalogInput) p_source).getValue();
      }
      else if (p_source.getName().equals(SwerveInputs.TEST_HALL_EFFECT.getName()))
      {
         m_hallEffect = ((DigitalInput) p_source).getValue();
      }
//      else if (p_source.getName().equals(SwerveInputs.POT.getName()))
//      {
//      }
      else if (p_source.getName().equals(SwerveInputs.DRV_RIGHT_Y.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.TEST_ABSOLUTE_ENCODER.getName()))
      {
         m_currentEncoder = ((AnalogInput) p_source).getValue();
      }
      // else if (p_source.getName().equals(SwerveInputs.DRV_DPAD_X.getName()))
      // {
      // }
      // else if (p_source.getName().equals(SwerveInputs.DRV_DPAD_Y.getName()))
      // {
      // }
   }

   @Override
   public void init()
   {
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_1.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_3.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_4.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_5.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_6.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_7.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_8.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_9.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_10.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_11.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_BUTTON_12.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_ROTATION.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_HEADING_Y.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_HEADING_X.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.DRV_RIGHT_Y.getName()).addInputListener(this);
      // Core.getInputManager().getInput(SwerveInputs.DRV_DPAD_X.getName()).addInputListener(this);
      // Core.getInputManager().getInput(SwerveInputs.DRV_DPAD_Y.getName()).addInputListener(this);

      Core.getInputManager().getInput(SwerveInputs.TEST_HALL_EFFECT.getName()).addInputListener(this);
      Core.getInputManager().getInput(SwerveInputs.TEST_ABSOLUTE_ENCODER.getName()).addInputListener(this);
      
//      Core.getInputManager().getInput(SwerveInputs.POT.getName()).addInputListener(this);
      
      // Retrieve the drive motor outputs
      m_frontLeftDrive = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.FRONT_LEFT.getName()));
      m_frontRightDrive = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.FRONT_RIGHT.getName()));
      m_rearLeftDrive = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.REAR_LEFT.getName()));
      m_rearRightDrive = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.REAR_RIGHT.getName()));
      m_frontLeftRotate = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.FRONT_LEFT_ROT.getName()));
      m_frontRightRotate = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.FRONT_RIGHT_ROT.getName()));
      m_rearLeftRotate = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.REAR_LEFT_ROT.getName()));
      m_rearRightRotate = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.REAR_RIGHT_ROT.getName()));
   }

   @Override
   public void update()
   {
      SwerveBaseState currentState = null;
      
      if (m_hallEffect)
      {
         DriverStation.reportError("Switch on\n", false);
      }
      else
      {
         DriverStation.reportError("Switch off\n", false);
      }
      
      s_log.fine("Encoder: " + s_format.format(m_currentEncoder) + "\n");
      
      
      if (m_recalcMode)
      {
         recalculateDriveMode();
      }
      
      switch (m_mode)
      {
         case CRAB:
            currentState = m_crabDriveMode.calculateNewState(m_prevState, m_headingX, m_headingY);
            break;
         case SWERVE:
            currentState = m_swerveDriveMode.calculateNewState(m_prevState, m_headingX, m_headingY, m_joystickRotation);
            break;
         // TODO: Add any more modes here
         default:
            currentState = m_crabDriveMode.calculateNewState(m_prevState, m_headingX, m_headingY);
            break;
      }
      
      updateModuleStates(currentState);
      
      m_prevState = currentState;
   }

   private void updateModuleStates(SwerveBaseState state)
   {
      // Rotate wheels to heading
      m_frontLeftRotate.setValue(calculateRotationSpeed(m_prevState.getFrontLeft().getRotationAngle(), state.getFrontLeft().getRotationAngle()));
      m_frontRightRotate.setValue(calculateRotationSpeed(m_prevState.getFrontRight().getRotationAngle(), state.getFrontRight().getRotationAngle()));
      m_rearLeftRotate.setValue(calculateRotationSpeed(m_prevState.getRearLeft().getRotationAngle(), state.getRearLeft().getRotationAngle()));
      m_rearRightRotate.setValue(calculateRotationSpeed(m_prevState.getRearRight().getRotationAngle(), state.getRearRight().getRotationAngle()));

      // TODO - Set angle for rotation PID

      // Set motor speed
      m_frontLeftDrive.setValue(state.getFrontLeft().getSpeed());
      m_frontRightDrive.setValue(state.getFrontRight().getSpeed());
      m_rearLeftDrive.setValue(state.getRearLeft().getSpeed());
      m_rearRightDrive.setValue(state.getRearRight().getSpeed());
   }
   
   private double calculateRotationSpeed(int p_prev, int p_target)
   {
      double result = 0.0;
      
      // Usually the angle changes will be small.  For large changes (> 180 difference)
      // follow the shortest path to the new position
      // Smooth the output so that it slows near the target position
      // Limit the minimum output to some percentage (20%?) to prevent stalling
      
      int distanceToTarget = Math.abs(p_target - p_prev);
      
      boolean invertDirection = false;
      
      if (distanceToTarget > 180)
      {
         invertDirection = true;
         distanceToTarget = 360 - distanceToTarget;
      }
      else if (p_target < p_prev)
      {
         invertDirection = true;
      }

      // Determine the speed of the motor
      // Scale based on proportion of distance to travel of 180 degrees
      // - 180 degrees away results in full speed
      // - closer is slower
      // - limit minimum output to 15%
      result = distanceToTarget / 180;
      if (distanceToTarget < 1)
      {
         result = 0.0;
      }
      else if (result < 0.15)
      {
         result = 0.15;
      }

      // Flip the output direction if we determined we should
      if (invertDirection)
      {
         result *= -1;
      }
      
      return result;
   }

   private void recalculateDriveMode()
   {
      // Set m_mode based on which button is pressed for different drive modes, if any
      // We can set it directly in inputUpdate(), or use this for more modes by combining buttons
      // for more values
   }
   
   @Override
   public void selfTest()
   {
   }

   @Override
   public String getName()
   {
      return WSSubsystems.SWERVE_BASE.getName();
   }

}
