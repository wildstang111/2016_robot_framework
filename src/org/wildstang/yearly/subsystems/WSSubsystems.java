package org.wildstang.yearly.subsystems;

public enum WSSubsystems
{
   DRIVE_BASE("Drive base"),
   LED("LED"),
   MONITOR("Monitor");

   private String m_name;

   WSSubsystems(String p_name)
   {
      m_name = p_name;
   }

   public String getName()
   {
      return m_name;
   }

}
