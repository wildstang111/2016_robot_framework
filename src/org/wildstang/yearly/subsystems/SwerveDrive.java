package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.SwerveInputs;
import org.wildstang.yearly.robot.WSOutputs;
import org.wildstang.yearly.robot.WSSubsystems;

public class SwerveDrive implements Subsystem
{
   private static final int CRAB = 0;
   
   private final double c = -0.0802884041;
   private final double b = 60.81576;
   private final double a = -15.574181;

   private double m_joystickRotation = 0.0;
   private double m_headingX = 0.0;
   private double m_headingY = 0.0;
   
   private int m_prevHeadingAngle = 0;
   private int m_mode = CRAB;
   private boolean m_recalcMode = false;

   // Drive motor outputs
   private WsVictor m_frontLeft = null;
   private WsVictor m_frontRight = null;
   private WsVictor m_rearLeft = null;
   private WsVictor m_rearRight = null;

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
      else if (p_source.getName().equals(SwerveInputs.POT.getName()))
      {
      }
      else if (p_source.getName().equals(SwerveInputs.DRV_RIGHT_Y.getName()))
      {
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

      Core.getInputManager().getInput(SwerveInputs.POT.getName()).addInputListener(this);
      
      // Retrieve the drive motor outputs
      m_frontLeft = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.FRONT_LEFT.getName()));
      m_frontRight = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.FRONT_RIGHT.getName()));
      m_rearLeft = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.REAR_LEFT.getName()));
      m_rearRight = (WsVictor)(Core.getOutputManager().getOutput(WSOutputs.REAR_RIGHT.getName()));
   }

   @Override
   public void update()
   {
      if (m_recalcMode)
      {
         recalculateDriveMode();
      }
      
      switch (m_mode)
      {
         case CRAB:
            crabDrive();
            break;
         // TODO: Add any more modes here
         default:
            crabDrive();
            break;
      }
      
   }

   private void recalculateDriveMode()
   {
      // Set m_mode based on which button is pressed for different drive modes, if any
      // We can set it directly in inputUpdate(), or use this for more modes by combining buttons
      // for more values
   }
   
   private void crabDrive()
   {
      // Crab drive is field oriented and does not allow the robot to rotate
      int currentHeadingAngle = (int)cartesianToDegrees(m_headingX, m_headingY);

      // Rotate wheels to heading
      // TODO - Set angle for rotation PID

      
      // Calculate the speed based on joystick position
      // TODO - use polar coordinate transformation, not just sqrt
      // For now, use sqrt / 1.42  (divide to scale to a max of 1.0)
      double motorSpeed = Math.sqrt((m_headingX * m_headingX) + (m_headingY * m_headingY)) / 1.42;
      
      // Set motor speed
      m_frontLeft.setValue(motorSpeed);
      m_frontRight.setValue(motorSpeed);
      m_rearLeft.setValue(motorSpeed);
      m_rearRight.setValue(motorSpeed);
   }
   
   double cartesianToDegrees(double x, double y)
   {
      double result = 0.0;
      
      if (x >= 0)
      {
         if (y >= 0)
         {
            if (y > x)
            {
               result =  f(x / y);
            }
            else if (x == 0)
            {
               result =  0;
            }
            else
            {
               result =  90 - f(y / x);
            }
         }
         else if (-y <= x)
         {
            result =  90 + f(-y / x);
         }
         else
         {
            result =  180 - f(-x / y);
         }
      }
      else if (y <= 0)
      {
         if (y <= x)
         {
            result =  180 + f(x / y);
         }
         else
         {
            result =  270 - f(y / x);
         }
      }
      else if (y <= -x)
      {
         result =  270 + f(-y / x);
      }

      else
      {
         result =  360 - f(-x / y);
      }
      
      return result;
   }

   double f(double t)
   {
      return t * (a * t + b) + c;
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
