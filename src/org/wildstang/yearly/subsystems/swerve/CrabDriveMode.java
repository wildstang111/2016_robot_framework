package org.wildstang.yearly.subsystems.swerve;

public class CrabDriveMode implements SwerveMode
{
   private final double c = -0.0802884041;
   private final double b = 60.81576;
   private final double a = -15.574181;

   
   @Override
   public SwerveBaseState calculateNewState(SwerveBaseState p_prevState, double... args)
   {
      
      double headingX = args[0];
      double headingY = args[1];
      
      WheelModuleState newFrontLeft = new WheelModuleState();
      WheelModuleState newFrontRight = new WheelModuleState();
      WheelModuleState newRearLeft = new WheelModuleState();
      WheelModuleState newRearRight = new WheelModuleState();

      // Crab drive is field oriented and does not allow the robot to rotate
      int currentHeadingAngle = (int) cartesianToDegrees(headingX, headingY);

      newFrontLeft.setRotationAngle(currentHeadingAngle);
      newFrontRight.setRotationAngle(currentHeadingAngle);
      newRearLeft.setRotationAngle(currentHeadingAngle);
      newRearRight.setRotationAngle(currentHeadingAngle);

      // Calculate the speed based on joystick position
      // TODO - use polar coordinate transformation, not just sqrt
      // For now, use sqrt / 1.42 (divide to scale to a max of 1.0)
      double motorSpeed = Math.sqrt((headingX * headingX) + (headingY * headingY)) / 1.42;

      newFrontLeft.setSpeed(motorSpeed);
      newFrontRight.setSpeed(motorSpeed);
      newRearLeft.setSpeed(motorSpeed);
      newRearRight.setSpeed(motorSpeed);
      
      
      return new SwerveBaseState(newFrontLeft, newFrontRight, newRearLeft, newRearRight);
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
               result = f(x / y);
            }
            else if (x == 0)
            {
               result = 0;
            }
            else
            {
               result = 90 - f(y / x);
            }
         }
         else if (-y <= x)
         {
            result = 90 + f(-y / x);
         }
         else
         {
            result = 180 - f(-x / y);
         }
      }
      else if (y <= 0)
      {
         if (y <= x)
         {
            result = 180 + f(x / y);
         }
         else
         {
            result = 270 - f(y / x);
         }
      }
      else if (y <= -x)
      {
         result = 270 + f(-y / x);
      }

      else
      {
         result = 360 - f(-x / y);
      }

      return result;
   }

   double f(double t)
   {
      return t * (a * t + b) + c;
   }

}
