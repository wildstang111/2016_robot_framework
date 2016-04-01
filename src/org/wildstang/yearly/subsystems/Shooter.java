package org.wildstang.yearly.subsystems;

import org.wildstang.framework.config.Config;
import org.wildstang.framework.config.ConfigListener;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.hardware.crio.outputs.WsRelay;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter implements Subsystem, ConfigListener
{
   private boolean flySpeedToggle = true;
   private boolean flyWheelToggle = false;
   private boolean hoodPosition = false;
   private double flySpeed;
   private double milsecToMax = 1000;
   private long startTime;
   private long sysTime;
   double flyWheelDiff;
   private static final String highSpeedKey = ".highSpeedVictorVal";
   private static final String lowSpeedKey = ".lowSpeedVictorVal";
   private double highFlywheelSpeedConf;
   private double lowFlywheelSpeedConf;
   private double expectedHighRateConf;
   private double expectedLowRateConf;
   private static final String expectedHighRateKey = ".expectedHighFlywheelRate";
   private static final String expectedLowRateKey = ".expectedLowFlywheelRate";
   private double onSpeedFlyWheelDiff;
   private static final String onSpeedDiffKey = ".onSpeedDiff";
   private static final int ON_SPEED_FLYWHEEL_DIFF_DEFAULT = 75;
   private static final double HIGH_RATE_DEFAULT = 2300;
   private static final double LOW_RATE_DEFAULT = 2100;
   private static double HIGH_DEFAULT = 0.825;
   private static final double LOW_DEFAULT = 0.75;
   private static final Integer hoodUp = new Integer(WsDoubleSolenoidState.FORWARD.ordinal());
   private static final Integer hoodDown = new Integer(WsDoubleSolenoidState.REVERSE.ordinal());

   private WsVictor flyWheel;
   private WsDoubleSolenoid shooterHood;
   private Encoder flyWheelEncoder = new Encoder(4, 5, true, EncodingType.k4X);
   private WsRelay RingLight;

   @Override
   public void inputUpdate(Input source)
   {
      // TODO Auto-generated method stub
      if (source.getName().equals(WSInputs.MAN_BUTTON_3.getName()))
      {
         if (((DigitalInput) source).getValue() == true)
         {
            flyWheelToggle = !flyWheelToggle;
         }
         // manipulator button circle
      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_6.getName()))
      {
         if (((DigitalInput) source).getValue() == true)
         {
            hoodPosition = !hoodPosition;
         }
         // manipulator button R2
      }
      else if (source.getName().equals(WSInputs.DRV_BUTTON_2.getName()))
      {
         if (((DigitalInput) source).getValue() == true)
         {
            hoodPosition = false;
         }
      }
      else if (source.getName().equals(WSInputs.MAN_BUTTON_4.getName()))
      {
         if (((DigitalInput) source).getValue() == true)
         {
            flySpeedToggle = !flySpeedToggle;
         }
      }
   }

   @Override
   public void init()
   {
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_3.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_4.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.MAN_BUTTON_6.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName()).addInputListener(this);

      RingLight = (WsRelay) Core.getOutputManager().getOutput(WSOutputs.RING_LIGHT.getName());
      RingLight.enable();

      flyWheel = (WsVictor) (Core.getOutputManager().getOutput(WSOutputs.SHOOTER.getName()));
      shooterHood = ((WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.SHOOTER_HOOD.getName()));

      highFlywheelSpeedConf = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + highSpeedKey, HIGH_DEFAULT);
      lowFlywheelSpeedConf = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + lowSpeedKey, LOW_DEFAULT);

      expectedHighRateConf = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + expectedHighRateKey, HIGH_RATE_DEFAULT);
      expectedLowRateConf = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + expectedLowRateKey, LOW_RATE_DEFAULT);
      onSpeedFlyWheelDiff = Core.getConfigManager().getConfig().getInt(this.getClass().getName()
            + onSpeedDiffKey, ON_SPEED_FLYWHEEL_DIFF_DEFAULT);

      // Need to figure out these values to configure the encoder.
      // flyWheelEncoder.setMaxPeriod(0.1);
      // flyWheelEncoder.setMinRate(10);
      // flyWheelEncoder.setDistancePerPulse(5);
      // flyWheelEncoder.setReverseDirection(true);
      // flyWheelEncoder.setSamplesToAverage(7);

      flyWheelDiff = 0;
   }

   @Override
   public void update()
   {
      Integer hoodUpDown;
      double expectedRate;
      double outputAdjust = 0.0;
      double outputAdjustLimit;
      double flyWheelRate = flyWheelEncoder.getRate();

      hoodUpDown = hoodPosition ? hoodUp : hoodDown;

      if (true == flySpeedToggle)
      {
         flySpeed = .825;
         expectedRate = 2310;
      }
      else
      {
         flySpeed = .8;
         expectedRate = 2290;
      }

      if (true == flyWheelToggle)
      {
         sysTime = System.currentTimeMillis();
         if (sysTime < startTime + milsecToMax)
         {
            flyWheel.setValue((flySpeed)
                  * ((sysTime - startTime) / milsecToMax));
         }
         else
         {
            if (flyWheelRate != 0)
            {

               // Poor Man's PID.
               flyWheelDiff = Math.abs(flyWheelRate - expectedRate);
               outputAdjust = flyWheelDiff / expectedRate;

               // Limit the output Adjust to less than half of the expected Rate
               // to
               // temper this a bit.
               outputAdjustLimit = flySpeed / 2;
               if (outputAdjust > outputAdjustLimit) ;
               {
                  outputAdjust = outputAdjustLimit;
               }

               if (flyWheelRate > expectedRate)
               {
                  // Decrease Speed
                  SmartDashboard.putNumber("flyWheelRate greater than expectedRate", flySpeed);
                  flySpeed -= outputAdjust;
               }
               else if (flyWheelRate < expectedRate)
               {
                  SmartDashboard.putNumber("flyWheelRate less than expectedRate", flySpeed);
                  // Increase the speed.
                  flySpeed += outputAdjust;
               }

               // Cap the fly wheel speed to 1.0.
               flySpeed = flySpeed > 1.0 ? 1.0
                     : flySpeed < 0.0 ? 0.0 : flySpeed;
            }
            flyWheel.setValue(-flySpeed);
         }
      }
      else
      {
         flyWheel.setValue(0);
      }

      shooterHood.setValue(hoodUpDown);
      SmartDashboard.putNumber("Expected Rate", expectedRate);
      SmartDashboard.putNumber("Raw Flywheel", -flySpeed);
      SmartDashboard.putString("Flywheel Speed", flySpeedToggle ? "High"
            : "Low");
      SmartDashboard.putNumber("Flywheel Rate", flyWheelRate);
      SmartDashboard.putNumber("Flywheel Output Adjustment", outputAdjust);
      SmartDashboard.putString("Flywheel", flyWheelToggle ? "On" : "Off");
      SmartDashboard.putString("Hood Positition", hoodPosition ? "Up" : "Down");
      SmartDashboard.putBoolean("Flywheel at Speed", doesSpeedMatch());
   }

   @Override
   public void selfTest()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public String getName()
   {
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
      return flyWheelToggle;
   }

   public boolean doesSpeedMatch()
   {
      return flyWheelDiff <= onSpeedFlyWheelDiff ? true : false;
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
      onSpeedFlyWheelDiff = p_newConfig.getInt(this.getClass().getName()
            + onSpeedDiffKey, ON_SPEED_FLYWHEEL_DIFF_DEFAULT);
   }

   public void shooterOverride(boolean state)
   {
      flyWheelToggle = state;
   }

}
