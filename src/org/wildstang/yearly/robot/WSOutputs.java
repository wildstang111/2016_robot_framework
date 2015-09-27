package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Outputs;
import org.wildstang.framework.io.outputs.OutputType;
import org.wildstang.hardware.crio.outputs.WSOutputType;

public enum WSOutputs implements Outputs
{
//   // MOTORS
//   om.addOutput(RIGHT_DRIVE_SPEED, new WsDriveSpeed("Right Drive Speed", 2, 3));
//   om.addOutput(LEFT_DRIVE_SPEED, new WsDriveSpeed("Left Drive Speed", 0, 1));
//   om.addOutput(STRAFE_DRIVE_SPEED, new WsDriveSpeed("Strafe Drive Speed", 4, 5));
//   om.addOutput(LIFT_A, new WsVictor("Lift A", 6));
//   om.addOutput(LIFT_B, new WsVictor("Lift B", 7));
//   om.addOutput(INTAKE_WHEEL_RIGHT, new WsVictor("Right Intake Wheels", 9));
//   om.addOutput(INTAKE_WHEEL_LEFT, new WsVictor("Left Intake Wheel", 8));
//
//   // SOLENOIDS
//   om.addOutput(SHIFTER, new WsDoubleSolenoid("Shifter", 0, 1));
//   om.addOutput(PAWL_RELEASE, new WsSolenoid("Pawl Release", 2));
//   om.addOutput(INTAKE_PISTONS, new WsSolenoid("Intake Pistons", 4));
//   om.addOutput(TOP_CONTAINMENT, new WsSolenoid("Top Containment", 6));
//   om.addOutput(BIN_GRABBER, new WsDoubleSolenoid("Bin Grabbers", 3, 5));

   RIGHT_DRIVE_1("Right drive 1",            WSOutputType.VICTOR,    null,    0,  0.0, true),
   RIGHT_DRIVE_2("Right drive 2",            WSOutputType.VICTOR,    null,    1,  0.0, true),
   LEFT_DRIVE_1("Left drive 1",              WSOutputType.VICTOR,    null,    2,  0.0, true),
   LEFT_DRIVE_2("Left drive 2",              WSOutputType.VICTOR,    null,    3,  0.0, true),
   STRAFE_DRIVE_1("Strafe drive 2",          WSOutputType.VICTOR,    null,    4,  0.0, true),
   STRAFE_DRIVE_2("Strafe drive 2",          WSOutputType.VICTOR,    null,    5,  0.0, true),
   LIFT_A("Lift A",                          WSOutputType.VICTOR,    null,    6,  0.0, true),
   LIFT_B("Lift B",                          WSOutputType.VICTOR,    null,    7,  0.0, true),
   INTAKE_WHEEL_LEFT("Intake wheel left",    WSOutputType.VICTOR,    null,    8,  0.0, true),
   INTAKE_WHEEL_RIGHT("Intake wheel right",  WSOutputType.VICTOR,    null,    9,  0.0, true),

   // Solenoids
   SHIFTER("Shifter",                  WSOutputType.SOLENOID_DOUBLE, 0,    0, 1,  0.0, true),
   PAWL("Pawl",                        WSOutputType.SOLENOID_SINGLE, 0,    2,  0.0, true),
   INTAKE_PISTONS("Intake pistons",    WSOutputType.SOLENOID_SINGLE, 0,    4,  0.0, true),
   TOP_CONTAINMENT("Containment",      WSOutputType.SOLENOID_SINGLE, 0,    6,  0.0, true),
   BIN_GRABBER("Bin grabber",          WSOutputType.SOLENOID_DOUBLE, 0,    3, 5,  0.0, true);

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
