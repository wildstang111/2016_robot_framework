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
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

public class Intake implements Subsystem
{
   // add variables here

   private boolean intakeSensorReading;
   private boolean rollerMovingIn;
   private boolean rollerMovingOut;
   private boolean nosePneumatic;
   private boolean deployPneumatic;
   private boolean manRollerInOverride;
   private boolean manNoseControl;
   private boolean drvNoseControl;
   private boolean manDeployPneumaticControl;
   private double manLeftJoyRollerIn;
   private static double rollerInDeadband = .5;
   private static double rollerOutDeadband = -.5;

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
         manLeftJoyRollerIn = ((AnalogInput) source).getValue();
      }

      // sets manNoseControl to Manipulator button 6
      if (source.getName().equals(WSInputs.MAN_BUTTON_6.getName()))
      {
         manNoseControl = ((DigitalInput) source).getValue();
      }

      // sets drvNoseControl to Drive Button 6
      if (source.getName().equals(WSInputs.DRV_BUTTON_6.getName()))
      {
         drvNoseControl = ((DigitalInput) source).getValue();
      }

      // setting manRollerInOverride to Manipulator button 1
      if (source.getName().equals(WSInputs.MAN_BUTTON_1.getName()))
      {
         manRollerInOverride = ((DigitalInput) source).getValue();
      }

      // setting manDeployPneumaticControl to Manipulator button 8
      if (source.getName().equals(WSInputs.MAN_BUTTON_8.getName()))
      {
         manDeployPneumaticControl = ((DigitalInput) source).getValue();
      }
   }

   @Override
   public void init()
   {
      // TODO Auto-generated method stub

      // asking for below Inputs
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_6.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_6.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_8.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_1.getName()).addInputListener(this);
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

      // tells status of manLeftJoyRollerIn, intakeSensorReading, and rollerMovingIn
      System.out.println("rollerMovingIn= " + rollerMovingIn + " rollerMovingOut= " + rollerMovingOut 
            + " manLeftJoyRollerIn= " + manLeftJoyRollerIn);

      //Puts the nose pneumatic in motion when either the drvNoseControl or
      //man nose control are true
      if (drvNoseControl == true || manNoseControl == true)
      {
         nosePneumatic = true;
         rollerMovingIn = false;
         rollerMovingOut = false;
      } else if (drvNoseControl == false && manNoseControl == false){
         nosePneumatic = false;
      }

      // toggles deployPneumatic to manDeployPneumaticControl
      if (manDeployPneumaticControl == true)
      {
         deployPneumatic = true;
         rollerMovingIn = false;
         rollerMovingOut = false;
      } else if (manDeployPneumaticControl == false) {
         deployPneumatic = false;
      }

      // if you push the left joy stick up, the intake will roll inwards.
      // if you push the left joy stick down, the intake will roll outwards.
      if (manLeftJoyRollerIn >= rollerInDeadband)
      {
         rollerMovingOut = false;
         rollerMovingIn = true;
      }
      else if (manLeftJoyRollerIn <= rollerOutDeadband)
      {
         rollerMovingIn = false;
         rollerMovingOut = true;
      } else {
         if (manRollerInOverride == false && manLeftJoyRollerIn < .5 && manLeftJoyRollerIn > -.5) {
            rollerMovingIn = false;
            rollerMovingOut = false;
         }
      }

      

      if (manRollerInOverride == true)
      {
         rollerMovingIn = true;
         intakeSensorReading = false;
      }
      
      // if intakeSensorReading is true, the roller will stop moving in
      if (intakeSensorReading == true)
      {
         rollerMovingIn = false;
         rollerMovingOut = false;
      }

      // if rollerMovingIn is true, the roller will move inwards
      if (rollerMovingIn == true)
      {
         ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER.getName())).setValue(0.75);
      }

      // if rollerMovingOut is true, the roller will move in reverse
      if (rollerMovingOut == true)
      {
         ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER.getName())).setValue(-0.75);
      }
      
      if (deployPneumatic == true) {
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.INTAKE_DEPLOY.getName())).setValue(true);
      } else {
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.INTAKE_DEPLOY.getName())).setValue(false);
      }
      
      if (nosePneumatic == true) {
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.INTAKE_FRONT_LOWER.getName())).setValue(true);
      } else {
         ((WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.INTAKE_FRONT_LOWER.getName())).setValue(false);
      }

      /*
       * // buttonPress controls DIO_LED_0 etc. ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.DIO_LED_0.getName())).
       * setValue(manLeftJoyRollerIn >= .5); ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.SENSOR_LED_1.getName())).
       * setValue(intakeSensorReading); ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER_LED_2.
       * getName())).setValue(rollerMovingIn); // ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.Pneumatic_1.getName())).
       * setValue(nosePneumatic); // ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.Pneumatic_2.getName())).
       * setValue(deployPneumatic); ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.Pneumatic_1_LED.getName())
       * ).setValue(nosePneumatic); ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.Pneumatic_2_LED.getName())
       * ).setValue(deployPneumatic);
       */
   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

}
