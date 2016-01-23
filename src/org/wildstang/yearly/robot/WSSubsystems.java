package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Subsystems;
import org.wildstang.yearly.subsystems.DriveBase;
import org.wildstang.yearly.subsystems.LED;
import org.wildstang.yearly.subsystems.Monitor;
//import org.wildstang.yearly.subsystems.SwerveDrive;
import org.wildstang.yearly.subsystems.Intake;

public enum WSSubsystems implements Subsystems
{
   //SWERVE_BASE("Swerve base", SwerveDrive.class),
   //LED("LED", LED.class),
   MONITOR("Monitor", Monitor.class),
   INTAKE("Intake", Intake.class);

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
