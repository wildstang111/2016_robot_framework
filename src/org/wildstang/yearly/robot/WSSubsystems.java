package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Subsystems;
import org.wildstang.yearly.subsystems.ButtonPresetTests;
import org.wildstang.yearly.subsystems.DriveBase;
import org.wildstang.yearly.subsystems.IMUTest;
import org.wildstang.yearly.subsystems.LED;
import org.wildstang.yearly.subsystems.Monitor;
import org.wildstang.yearly.subsystems.Shooter;
import org.wildstang.yearly.subsystems.SwerveDrive;

public enum WSSubsystems implements Subsystems
{
//	SIXWHEEL_BASE("Six Wheel", DriveBase.class),
   //SWERVE_BASE("Swerve base", SwerveDrive.class),
   LED("LED", LED.class),
   MONITOR("Monitor", Monitor.class),
//   IMUTEST("IMU Test", IMUTest.class);
   SHOOTER("Shooter", Shooter.class);
//   BUTTONTEST("Test System", ButtonPresetTests.class);
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
