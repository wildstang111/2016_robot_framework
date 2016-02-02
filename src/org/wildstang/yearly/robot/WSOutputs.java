package org.wildstang.yearly.robot;

// expand this and edit if trouble with Ws
import org.wildstang.framework.core.Outputs;
import org.wildstang.framework.hardware.OutputConfig;
import org.wildstang.framework.io.outputs.OutputType;
import org.wildstang.hardware.crio.outputs.WSOutputType;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.hardware.crio.outputs.config.WsDoubleSolenoidConfig;
import org.wildstang.hardware.crio.outputs.config.WsI2COutputConfig;
import org.wildstang.hardware.crio.outputs.config.WsSolenoidConfig;
import org.wildstang.hardware.crio.outputs.config.WsTalonConfig;
import org.wildstang.hardware.crio.outputs.config.WsVictorConfig;

import edu.wpi.first.wpilibj.I2C;

public enum WSOutputs implements Outputs
{
   //DO NOT REMOVE THE FORMATTER COMMENT.  DO NOT PLACE ANYTHING FOR ENUMERATIONS IN FRONT OF THE @formatter::off
   //Disable the formatter in this section of code, so it doesn't make the enumeration completely unreadable 
   // @formatter:off
   
   //Motors
   LEFT_1("Left motor 1",            WSOutputType.VICTOR,    new WsVictorConfig(0, 0.0), true),
   LEFT_2("Left motor 2",          WSOutputType.VICTOR,    new WsVictorConfig(1, 0.0), true),
   RIGHT_1("Right motor 1",              WSOutputType.VICTOR,    new WsVictorConfig(2, 0.0), true),
   RIGHT_2("Right motor 2",            WSOutputType.VICTOR,    new WsVictorConfig(3, 0.0), true),
   
   //Switch over to a talon
   //SHOOTER("Shooter flywheel Victor",     WSOutputType.VICTOR,    new WsVictorConfig(6, 0.0), true),
   SHOOTER("Shooter Flywheel Talon", WSOutputType.TALON, new WsTalonConfig(4, 0.0), true),
   
   WINCH_LEFT("Left Winch", WSOutputType.VICTOR, new WsVictorConfig(4, 0.0), true),
   WINCH_RIGHT("Right Winch", WSOutputType.VICTOR, new WsVictorConfig(5, 0.0), true),
   FRONT_ROLLER("Front intake roller",     WSOutputType.VICTOR,    new WsVictorConfig(7, 0.0), true),
   
   //I2C
   LED("LEDs",                               WSOutputType.I2C, new WsI2COutputConfig(I2C.Port.kOnboard, 0x10), true),

   // Solenoids
   SHIFTER("Shifter double solenoid", WSOutputType.SOLENOID_DOUBLE, new WsDoubleSolenoidConfig(1, 1, 2, WsDoubleSolenoidState.FORWARD), true),
   INTAKE_DEPLOY("Intake deploy", WSOutputType.SOLENOID_SINGLE, new WsSolenoidConfig(1, 3, false), true),
   INTAKE_FRONT_LOWER("Intake front lower", WSOutputType.SOLENOID_SINGLE, new WsSolenoidConfig(1, 4, false), true),
   LOWER_ARM("Lower Lift Arm", WSOutputType.SOLENOID_SINGLE, new WsSolenoidConfig(1, 5, false), true),
   UPPER_ARM("Upper Lift Arm", WSOutputType.SOLENOID_SINGLE, new WsSolenoidConfig(1, 6, false), true),
   HOOKS("Hook Extenstion", WSOutputType.SOLENOID_DOUBLE, new WsDoubleSolenoidConfig(1, 7, 8, WsDoubleSolenoidState.REVERSE), true),
   SHOOTER_HOOD("Shooter Hood", WSOutputType.SOLENOID_DOUBLE, new WsDoubleSolenoidConfig(2, 1, 2, WsDoubleSolenoidState.REVERSE), true),
   WINCH_BRAKE("Winch Brakeput", WSOutputType.SOLENOID_SINGLE, new WsSolenoidConfig(2, 3, false), true);	   
   
   //Reenable the formatter after this section of code, so it can reformat the rest of the file.
   //DO NOT REMOVE THIS COMMENT.  IT SHOULD ALWAYS BE AFTER THE LAST ENUMERATION DEFINITION
   // @formatter:on   
   
   private String m_name;
   private OutputType m_type;
   private OutputConfig m_config;
   private boolean m_trackingState;

   WSOutputs(String p_name, OutputType p_type, OutputConfig p_config, boolean p_trackingState)
   {
      m_name = p_name;
      m_type = p_type;
      m_config = p_config;
      m_trackingState = p_trackingState;
   }
   
  
   @Override
   public String getName()
   {
      return m_name;
   }
   
   @Override
   public OutputType getType()
   {
      return m_type;
   }
   
   public OutputConfig getConfig()
   {
      return m_config;
   }

   public boolean isTrackingState()
   {
      return m_trackingState;
   }

}
