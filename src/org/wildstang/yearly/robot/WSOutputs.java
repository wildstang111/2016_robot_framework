package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Outputs;
import org.wildstang.framework.io.outputs.OutputType;
import org.wildstang.hardware.crio.outputs.WSOutputType;

public enum WSOutputs implements Outputs
{
   VICTOR("Victor",          WSOutputType.VICTOR,    null,    0,  0.0, true),
   TALON("Talon",            WSOutputType.TALON,     null,    1,  0.0, true),
   VICTOR_SP("Victor SP",    WSOutputType.VICTOR,    null,    2,  0.0, true),
   SPIKE("Spike",            WSOutputType.RELAY,     null,    3,  0.0, true),

   // Solenoids
   DOUBLE("Double solenoid",                  WSOutputType.SOLENOID_DOUBLE, 0,    0, 1,   0.0, true),
   SINGLE("Single solenoid",                  WSOutputType.SOLENOID_SINGLE, 0,    2,      0.0, true);

   private String m_name;
   private OutputType m_type;
   private Object m_port;
   private Object m_port2;
   private Object m_default;
   private Object m_module;
   private boolean m_trackingState;

   WSOutputs(String p_name, OutputType p_type, Object p_module, int p_port, boolean p_trackingState)
   {
      m_name = p_name;
      m_type = p_type;
      m_module = p_module;
      m_port = p_port;
      m_trackingState = p_trackingState;
   }
   
   WSOutputs(String p_name, OutputType p_type, Object p_module, int p_port, boolean p_default, boolean p_trackingState)
   {
      m_name = p_name;
      m_type = p_type;
      m_module = p_module;
      m_port = p_port;
      m_default = p_default;
      m_trackingState = p_trackingState;
   }
   
   WSOutputs(String p_name, OutputType p_type, Object p_module, int p_port, double p_default, boolean p_trackingState)
   {
      m_name = p_name;
      m_type = p_type;
      m_module = p_module;
      m_port = p_port;
      m_default = p_default;
      m_trackingState = p_trackingState;
   }
   
   WSOutputs(String p_name, OutputType p_type, Object p_module, int p_port, int p_port2, double p_default, boolean p_trackingState)
   {
      m_name = p_name;
      m_type = p_type;
      m_module = p_module;
      m_port = p_port;
      m_port2 = p_port2;
      m_default = p_default;
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
   
   @Override
   public Object getPort()
   {
      return m_port;
   }
   

   @Override
   public Object getDefault()
   {
      return m_default;
   }

   public boolean isTrackingState()
   {
      return m_trackingState;
   }

   @Override
   public Object getModule()
   {
      return m_module;
   }
   
   public Object getPort2()
   {
      return m_port2;
   }

}
