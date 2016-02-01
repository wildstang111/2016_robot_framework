package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Subsystems;
import org.wildstang.yearly.subsystems.Climber;
import org.wildstang.yearly.subsystems.DriveBase;
import org.wildstang.yearly.subsystems.Intake;
import org.wildstang.yearly.subsystems.LED;
import org.wildstang.yearly.subsystems.Monitor;
import org.wildstang.yearly.subsystems.Shooter;

public enum WSSubsystems implements Subsystems
{
   DRIVE_BASE("Swerve base", DriveBase.class),
   LED("LED", LED.class),
   MONITOR("Monitor", Monitor.class),
   INTAKE("Intake", Intake.class),
   SHOOTER("Shooter", Shooter.class),
   CLIMBER("Climber", Climber.class);
   // BUTTONTEST("Test System", ButtonPresetTests.class);
   private String m_name;
   private Class m_class;

   WSSubsystems(String p_name, Class p_class)
   {
      m_name = p_name;
      m_class = p_class;
   }

   @Override
   public String getName()
   {
      return m_name;
   }

   @Override
   public Class getSubsystemClass()
   {
      return m_class;
   }

}
