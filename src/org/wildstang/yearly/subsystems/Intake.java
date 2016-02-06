package org.wildstang.yearly.subsystems;

//expand this and edit if trouble with Ws
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.outputs.AnalogOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

public class Intake implements Subsystem
{
   // add variables here

   private boolean sensorReading;
   private boolean rollerMovingIn;
   private boolean rollerMovingOut;
   private boolean intakeNosePneumatic = false;
   private boolean nosePrevious = false;
   private boolean drvNosePrevious = false;
   private boolean deployIntakePneumatic = false;
   private boolean deployPrevious = false;
   private boolean manRollerInOverride;
   private boolean manIntakeNoseControl;
   private boolean drvIntakeNoseControl;
   private boolean mandeployIntakePneumaticControl;
   private boolean manRollerInOverrideCurrentState;
   private boolean manRollerInOverrideOldState;
   private double manLeftJoyRollerIn;
   private boolean getLow;

   @Override
   public void inputUpdate(Input source)
   {
      // TODO Auto-generated method stub

      // does something with Inputs and variables

      // setting sensorReading to DIO_0_INTAKE_SENSOR
      if (source.getName().equals(WSInputs.INTAKE_BOLDER_SENSOR.getName()))
      {
         sensorReading = ((DigitalInput) source).getValue();
      }

      // setting manLeftJoyRollerIn to the left joystick's y axis
      if (source.getName().equals(WSInputs.MAN_LEFT_JOYSTICK_Y.getName()))
      {
         manLeftJoyRollerIn = ((AnalogInput) source).getValue();
      }

      if (source.getName().equals(WSInputs.DRV_BUTTON_2.getName()))
      {
         getLow = ((DigitalInput) source).getValue();
      }
      // sets drvIntakeNoseControl to Drive Button 6
      if (source.getName().equals(WSInputs.DRV_BUTTON_6.getName()))
      {
         drvIntakeNoseControl = ((DigitalInput) source).getValue();
      }
      // sets manIntakeNoseControl to Manipulator button 6
      if (source.getName().equals(WSInputs.MAN_BUTTON_6.getName()))
      {
         manIntakeNoseControl = ((DigitalInput) source).getValue();
      }
      // setting mandeployIntakePneumaticControl to Manipulator button 8
      if (source.getName().equals(WSInputs.MAN_BUTTON_8.getName()))
      {
         mandeployIntakePneumaticControl = ((DigitalInput) source).getValue();
      }
      // setting manRollerInOverride to Manipulator button 1
      if (source.getName().equals(WSInputs.MAN_BUTTON_9.getName()))
      {
         manRollerInOverrideCurrentState = ((DigitalInput) source).getValue();
      }

   }

   @Override
   public void init()
   {
      // TODO Auto-generated method stub

      // asking for below Inputs
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_6.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_6.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_8.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_9.getName()).addInputListener(this);
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

      // toggles intakeNosePneumatic and deployIntakePneumatic to
      // manIntakeNoseControl
      // pneumatic1 is the nose; pneumatic2 is the intake deploy
      if (drvIntakeNoseControl == true && drvNosePrevious == false)
      {
         intakeNosePneumatic = !intakeNosePneumatic;
         ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.SHIFTER.getName())).setValue(new Integer(intakeNosePneumatic == true ?
               WsDoubleSolenoidState.FORWARD.ordinal() : WsDoubleSolenoidState.REVERSE.ordinal()));
      }
      drvNosePrevious = drvIntakeNoseControl;
      
      if (manIntakeNoseControl == true && nosePrevious == false)
      {
         intakeNosePneumatic = !intakeNosePneumatic;
         ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.SHIFTER.getName())).setValue(new Integer(intakeNosePneumatic == true ?
               WsDoubleSolenoidState.FORWARD.ordinal() : WsDoubleSolenoidState.REVERSE.ordinal()));
      }
      nosePrevious = manIntakeNoseControl;

      // toggles intakeNosePneumatic and deployIntakePneumatic to
      // drvIntakeNoseControl

      // toggles deployIntakePneumatic to mandeployIntakePneumaticControl
      if (mandeployIntakePneumaticControl == true && deployPrevious == false)
      {
         deployIntakePneumatic = !deployIntakePneumatic;
         ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.SHIFTER.getName())).setValue(new Integer(deployIntakePneumatic == true ?
               WsDoubleSolenoidState.FORWARD.ordinal() : WsDoubleSolenoidState.REVERSE.ordinal()));
      }
      deployPrevious = mandeployIntakePneumaticControl;

      // if you push the left joy stick up, the intake will roll inwards.
      // if you push the left joy stick down, the intake will roll outwards.
      if (manLeftJoyRollerIn >= .5)
      {
         rollerMovingOut = false;
         rollerMovingIn = true;
      }
      else if (manLeftJoyRollerIn <= -.5)
      {
         rollerMovingIn = false;
         rollerMovingOut = true;
      }

      // if the sensor is triggered, the roller will not move in unless
      // manRollerInOverride is pressed
      if (manRollerInOverrideOldState == false
            && manRollerInOverrideCurrentState == true)
      {
         if (manRollerInOverride == true)
         {
            sensorReading = false;
            rollerMovingIn = true;

         }
      }
      manRollerInOverrideOldState = manRollerInOverrideCurrentState;

      // if sensorReading is true, the roller will stop
      if (sensorReading == true)
      {
         rollerMovingIn = false;
      }

      // if rollerMovingOut is true, the roller will move inwards
      if (rollerMovingIn == true)
      {
         ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER.getName())).setValue(0.75);
      }

      // if rollerMovingOut is true, the roller will move in reverse
      if (rollerMovingOut == true)
      {
         ((AnalogOutput) Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER.getName())).setValue(-0.75);
      }

      /*
       * // buttonPress controls DIO_LED_0 etc. ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.DIO_LED_0.getName())).
       * setValue(manLeftJoyRollerIn >= .5); ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.SENSOR_LED_1.getName())).
       * setValue(sensorReading); ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.FRONT_ROLLER_LED_2.
       * getName())).setValue(rollerMovingIn); // ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.PNUMATIC_1.getName())).
       * setValue(intakeNosePneumatic); // ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.PNUMATIC_2.getName())).
       * setValue(deployIntakePneumatic); ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.PNUMATIC_1_LED.getName())
       * ).setValue(intakeNosePneumatic); ((DigitalOutput)
       * Core.getOutputManager().getOutput(WSOutputs.PNUMATIC_2_LED.getName())
       * ).setValue(deployIntakePneumatic);
       */
   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return null;
   }

   public boolean isDeployed()
   {
      return deployIntakePneumatic;
   }

   public boolean isNoseDeployed()
   {
      return intakeNosePneumatic;
   }

}
