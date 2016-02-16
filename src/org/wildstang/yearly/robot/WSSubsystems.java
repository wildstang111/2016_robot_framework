package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Subsystems;
import org.wildstang.yearly.subsystems.Climber;
import org.wildstang.yearly.subsystems.Intake;
import org.wildstang.yearly.subsystems.Monitor;
import org.wildstang.yearly.subsystems.Shooter;

public enum WSSubsystems implements Subsystems
{

   //DO NOT REMOVE THIS COMMENT.  DO NOT PLACE ANY ENUMERATION DEFINITIONS IN FRONT OF IT.
   //This keeps the formatter from completely making the enumeration unreadable.
   // @formatter::off
   MONITOR("Monitor", Monitor.class),
   INTAKE("Intake", Intake.class),
   SHOOTER("Shooter", Shooter.class),
   CLIMBER("Climber", Climber.class);
   
   //DO NOT REMOVE THIS COMMENT.  DO NOT PLACE ANY ENUMERATION DEFINITIONS AFTER IT.
   //This keeps the formatter from completely making the enumeration unreadable.
   // @formatter::on
   
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
