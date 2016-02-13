package org.wildstang.yearly.subsystems;

/* Please edit!!!
 * This program does all these things
 * reads INTAKE_BOLDER_SENSOR. INTAKE_BOLDER_SENSOR = intakeSensorReading.
 * intakeSensorReading stops rollerMovingIn unless manRollerInOverrideCurrentState is true and manRollerInOverrideOldState is false.
 * 
 * reads MAN_LEFT_JOYSTICK_Y. MAN_LEFT_JOYSTICK_Y = manLeftJoyRollerIn
 * prints out manLeftJoyRollerIn. if manLeftJoyRollerIn is greater than .5, rollerMovingIn is set to true and rollerMovingOut is set to false. this makes the roller move in
 * if manLeftJoyRollerIn is less than -.5, rollerMovingIn is set to false and rollerMovingOut is set to true. this makes the roller move out
 * 
 * status of manLeftJoyRollerIn, intakeSensorReading, and rollerMovingIn are printed out
 * 
 * reads MAN_BUTTON_6. MAN_BUTTON_6 = manNoseControl
 * changes nosePneumatic to true and deployPneumatic to false when manNoseControl is true
 * 
 * reads DRV_BUTTON_6. DRV_BUTTON_6 = drvNoseControl
 * changes nosePneumatic to true and deployPneumatic to false when drvNoseControl is true
 * 
 * reads MAN_BUTTON_1. MAN_BUTTON_1 = manRollerInStartNew
 * toggles rollerMovingIn with manRollerInStartNew & manRollerInStartOld, stops intakeSensorReading from being true
 * 
 * Continue...
 */

//expand this and edit if trouble with Ws
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.outputs.AnalogOutput;
import org.wildstang.framework.io.outputs.DigitalOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

public class Intake implements Subsystem
{
   // add variables here

   private boolean intakeSensorReading;
   private boolean nosePneumatic;
   private boolean deployPneumatic;
   private boolean manRollerInOverride;
   private boolean manNoseControl;
   private boolean drvNoseControl;
   private boolean manDeployPneumaticControl;
   private boolean intakeLimboNew;
   private boolean intakeLimboOld;
   private boolean limboOn;
   private boolean shoot;
   private double manLeftJoyRoller;
   private double rollerSpeed;

   @Override
   public void inputUpdate(Input source)
   {
      // TODO Auto-generated method stub

      // does something with Inputs and variables

      // setting intakeSensorReading to DIO_0_INTAKE_SENSOR
      if (source.getName().equals(WSInputs.INTAKE_BOLDER_SENSOR.getName()))
      {
         intakeSensorReading = ((DigitalInput) source).getValue();
      }

      // setting manLeftJoyRollerIn to the left joystick's y axis
      if (source.getName().equals(WSInputs.MAN_LEFT_JOYSTICK_Y.getName()))
      {
         manLeftJoyRoller = ((AnalogInput) source).getValue();
      }

      if (source.getName().equals(WSInputs.MAN_BUTTON_8.getName()))
      {
         shoot = ((DigitalInput) source).getValue();
      }

      // sets manNoseControl to Manipulator button 6
      if (source.getName().equals(WSInputs.MAN_BUTTON_5.getName()))
      {
         manNoseControl = ((DigitalInput) source).getValue();
      }

      // sets drvNoseControl to Drive Button 6
      if (source.getName().equals(WSInputs.DRV_BUTTON_5.getName()))
      {
         drvNoseControl = ((DigitalInput) source).getValue();
      }

      // setting manRollerInOverride to Manipulator button 1
      if (source.getName().equals(WSInputs.MAN_BUTTON_9.getName()))
      {
         manRollerInOverride = ((DigitalInput) source).getValue();
      }

      // setting manDeployPneumaticControl to Manipulator button 8
      if (source.getName().equals(WSInputs.MAN_BUTTON_7.getName()))
      {
         manDeployPneumaticControl = ((DigitalInput) source).getValue();
      }

      if (source.getName().equals(WSInputs.DRV_BUTTON_2.getName()))
      {
         intakeLimboNew = ((DigitalInput) source).getValue();
      }
   }

   @Override
   public void init()
   {
      // TODO Auto-generated method stub

      // asking for below Inputs
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_5.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_5.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_7.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_8.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_9.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_LEFT_JOYSTICK_Y.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.INTAKE_BOLDER_SENSOR.getName()).addInputListener(this);
   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub

      // does something with variables and Outputs

      // tells status of certain variables
      // System.out.println("shoot=" + shoot + " rollerSpeed= " + rollerSpeed);

      // Puts the nose pneumatic in motion when either the drvNoseControl or
      // man nose control are true
      if (drvNoseControl == true || manNoseControl == true)
      {
         nosePneumatic = true;
      }
      else
      {
         nosePneumatic = false;
      }

      // toggles deployPneumatic to manDeployPneumaticControl
      if (manDeployPneumaticControl == true)
      {
         deployPneumatic = true;
         rollerSpeed = 0;
      }
      else
      {
         deployPneumatic = false;
      }

      // if you push the left joy stick up, the intake will roll outwards.
      // if you push the left joy stick down, the intake will roll inwards.
      if (manLeftJoyRoller <= -0.5)
      {
         rollerSpeed = 0.75;
      }
      else if (manLeftJoyRoller >= 0.5)
      {
         rollerSpeed = -0.75;
      }
      else
      {
         rollerSpeed = 0;
      }

      if (intakeSensorReading == true)
      {
         rollerSpeed = 0;
      }

      if (manRollerInOverride == true)
      {
         rollerSpeed = 0.75;
      }

      if (shoot == true)
      {
         rollerSpeed = 0.75;
      }

      // Allows for toggling of limbo
      if (intakeLimboOld == false && intakeLimboNew == true)
      {
         if (deployPneumatic == true && nosePneumatic == true)
         {
            deployPneumatic = false;
            nosePneumatic = false;
         }
         else if (deployPneumatic == false || nosePneumatic == false)
         {
            deployPneumatic = true;
            nosePneumatic = true;
         }
      }
      intakeLimboOld = intakeLimboNew;

      // buttonPress controls DIO_LED_0 etc.
      // ((DigitalOutput)Core.getOutputManager().getOutput(WSOutputs.DIO_LED_0.getName())).setValue(manLeftJoyRollerIn
      // >= .5);
      // ((DigitalOutput)Core.getOutputManager().getOutput(WSOutputs.SENSOR_LED_1.getName())).setValue(intakeSensorReading);
      // ((DigitalOutput)Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER_LED_2.getName())).setValue(rollerMovingIn);
      // ((DigitalOutput)Core.getOutputManager().getOutput(WSOutputs.Pneumatic_1.getName())).setValue(nosePneumatic);
      // ((DigitalOutput)Core.getOutputManager().getOutput(WSOutputs.Pneumatic_2.getName())).setValue(deployPneumatic);
      ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.INTAKE_DEPLOY.getName())).setValue(deployPneumatic);
      ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.INTAKE_FRONT_LOWER.getName())).setValue(nosePneumatic);
      ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER.getName())).setValue(rollerSpeed);

   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

}
