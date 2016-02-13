package org.wildstang.yearly.subsystems;

import org.wildstang.framework.config.Config;
import org.wildstang.framework.config.ConfigListener;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter implements Subsystem, ConfigListener
{
   private boolean              currentFlySpeed, oldFlySpeed, flySpeedToggle = false;
   private boolean              currentFlyState, oldFlyState, flyWheelToggle = false;
   private boolean              hoodPosition   = false;
   private boolean              currentHoodState, oldHoodState;
   private double               flySpeed;
   private double               lowChange, highChange;
   private double               targetSpeed;
   private double               milsecToMax    = 1000;
   private long                 startTime;
   private long                 sysTime;
   double flyWheelDiff;
   private static final String  highSpeedKey   = ".highSpeedVictorVal";
   private static final String  lowSpeedKey    = ".lowSpeedVictorVal";
   private double               highFlywheelSpeedConf;
   private double               lowFlywheelSpeedConf;
   private double               expectedHighRateConf;
   private double               expectedLowRateConf;
   private static final String  expectedHighRateKey = ".expectedHighFlywheelRate";
   private static final String  expectedLowRateKey = ".expectedHighFlywheelRate";
   private static final double  OUTPUT_ADJUST_CAP = 0.5;
   private static final double  ON_SPEED_FLYWHEEL_DIFF = 100;
   private static final double  HIGH_RATE_DEFAULT = 3200;
   private static final double  LOW_RATE_DEFAULT = 2800;
   private static final double  HIGH_DEFAULT   = 0.75;
   private static final double  LOW_DEFAULT    = 0.70;
   private static final Integer hoodUp         = new Integer(WsDoubleSolenoidState.FORWARD.ordinal());
   private static final Integer hoodDown       = new Integer(WsDoubleSolenoidState.REVERSE.ordinal());

   private WsVictor             flyWheel;
   private WsDoubleSolenoid     shooterHood;
   private Encoder flyWheelEncoder;

   @Override
   public void inputUpdate(Input source)
   {
      // TODO Auto-generated method stub
      if (source.getName().equals(WSInputs.MAN_BUTTON_3.getName()))
      {
         // manipulator button circle
         currentFlyState = ((DigitalInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_5.getName()))
      {
         // manipulator button R2
         currentHoodState = ((DigitalInput) source).getValue();
      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_4.getName()))
      {
         currentFlySpeed = ((DigitalInput) source).getValue();
      }
   }

   @Override
   public void init()
   {
      // TODO Auto-generated method stub
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_3.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_4.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_5.getName()).addInputListener(this);

      flyWheel = (WsVictor) (Core.getOutputManager().getOutput(WSOutputs.SHOOTER.getName()));
      shooterHood = ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.SHOOTER_HOOD.getName()));
      flyWheelEncoder = new Encoder(4, 5, false, EncodingType.k4X);

      highFlywheelSpeedConf = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + highSpeedKey, HIGH_DEFAULT);
      lowFlywheelSpeedConf = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + lowSpeedKey, LOW_DEFAULT);
      
      expectedHighRateConf = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + expectedHighRateKey, HIGH_RATE_DEFAULT);
      expectedLowRateConf = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + expectedLowRateKey, LOW_RATE_DEFAULT);
      
      //Need to figure out these values to configure the encoder.
      flyWheelEncoder.setMaxPeriod(0.1);
      flyWheelEncoder.setMinRate(10);
      flyWheelEncoder.setDistancePerPulse(5);
      flyWheelEncoder.setReverseDirection(true);
      flyWheelEncoder.setSamplesToAverage(7);
   }

   private boolean calcToggle(boolean oldState, boolean currentState,
         boolean toggle)
   {
      boolean newToggle = currentState;
      if (oldState == false && currentState == true)
      {
         if (toggle == true)
         {
            startTime = System.currentTimeMillis();
            newToggle = false;
         }
         else if (toggle == false)
         {
            startTime = System.currentTimeMillis();
            newToggle = true;
         }
      }
      return newToggle;
   }

  
   @Override
   public void update()
   {
      Integer hoodUpDown;
      double expectedRate;
      double outputAdjust = 1.0;
      double flyWheelRate = flyWheelEncoder.getRate();
      
      flyWheelDiff = 0;

      flyWheelToggle = calcToggle(oldFlyState, currentFlyState, flyWheelToggle);
      oldFlyState = currentFlyState;

      hoodPosition = calcToggle(oldHoodState, currentHoodState, hoodPosition);
      oldHoodState = currentHoodState;

      hoodUpDown = hoodPosition ? hoodUp : hoodDown;

      flySpeedToggle = calcToggle(oldFlySpeed, currentFlySpeed, flySpeedToggle);
      oldFlySpeed = currentFlySpeed;
      
      if (flySpeedToggle == true)
      {
         flySpeed = highFlywheelSpeedConf;
         expectedRate = expectedHighRateConf;
      }
      else
      {
         flySpeed = lowFlywheelSpeedConf;
         expectedRate = expectedLowRateConf;
      }
      
      if (flyWheelToggle == true)
      {
         sysTime = System.currentTimeMillis();
         if (sysTime < startTime + milsecToMax)
         {
            flyWheel.setValue((flySpeed)
                  * ((sysTime - startTime) / milsecToMax));
         }
         else
         {
            //Poor Man's PID.
            flyWheelDiff = Math.abs(flyWheelRate - expectedRate);
            outputAdjust = flyWheelDiff / expectedRate;
            
            if (flyWheelRate > expectedRate)
            {
               //Cap the adjustment
               outputAdjust = outputAdjust < OUTPUT_ADJUST_CAP ? OUTPUT_ADJUST_CAP : outputAdjust;
            
               //Decrease Speed
               flySpeed *= outputAdjust;
            }
            else if (flyWheelRate < expectedRate)
            {
               //Cap the output Adjust
               outputAdjust = outputAdjust > OUTPUT_ADJUST_CAP ? OUTPUT_ADJUST_CAP : outputAdjust;
               //Increase the speed.
               flySpeed *= (1+outputAdjust);
            }
            //Cap the fly wheel speed to 1.0.
            flySpeed = flySpeed > 1.0 ? 1.0 : flySpeed < 0.0 ? 0.0 : flySpeed;
            
            flyWheel.setValue(flySpeed);
         }
      }
      else
      {
         flyWheel.setValue(0);
      }

      // targetSpeed = ???

      shooterHood.setValue(hoodUpDown);
      SmartDashboard.putNumber("Fly Wheel Speed", flySpeed);
      SmartDashboard.putNumber("Flywheel Rate", flyWheelRate);
      SmartDashboard.putBoolean("Fly Wheel Toggle State", flyWheelToggle);
      SmartDashboard.putBoolean("Hood State", hoodPosition);
      SmartDashboard.putBoolean("Is fly up to speed?", doesSpeedMatch());
   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public String getName()
   {
      // TODO Auto-generated method stub
      return "Shooter";
   }
   public boolean flySpeed()
   {
      return flySpeedToggle;
   }

   public boolean hoodPos()
   {
      return hoodPosition;
   }

   public boolean isOn()
   {
      return currentFlyState;
   }

   public boolean doesSpeedMatch()
   {
      return flyWheelDiff <= ON_SPEED_FLYWHEEL_DIFF ? true : false;
   }

   @Override
   public void notifyConfigChange(Config p_newConfig)
   {
      highFlywheelSpeedConf = p_newConfig.getDouble(this.getClass().getName()
            + highSpeedKey, HIGH_DEFAULT);
      lowFlywheelSpeedConf = p_newConfig.getDouble(this.getClass().getName()
            + lowSpeedKey, LOW_DEFAULT);
      expectedHighRateConf = p_newConfig.getDouble(this.getClass().getName()
            + expectedHighRateKey, HIGH_RATE_DEFAULT);
      expectedLowRateConf = p_newConfig.getDouble(this.getClass().getName()
            + expectedLowRateKey, LOW_RATE_DEFAULT);
      

   }

}
