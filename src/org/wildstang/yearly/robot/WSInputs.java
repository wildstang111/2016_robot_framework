package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Inputs;
import org.wildstang.framework.io.inputs.InputType;
import org.wildstang.hardware.JoystickConstants;
import org.wildstang.hardware.crio.inputs.WSInputType;






import edu.wpi.first.wpilibj.I2C.Port;


public enum WSInputs implements Inputs
{
// im.addSensorInput(LIDAR, new WsLIDAR());
//
   
   DRV_THROTTLE("Driver throttle", WSInputType.JS_JOYSTICK, 0, JoystickConstants.LEFT_JOYSTICK_Y, true),
   DRV_HEADING("Driver heading", WSInputType.JS_JOYSTICK, 0, JoystickConstants.RIGHT_JOYSTICK_X, true),
   DRV_DPAD_Y("Driver DPad Y", WSInputType.JS_DPAD, 0, JoystickConstants.DPAD_Y, true),
   DRV_DPAD_X("Driver DPad X", WSInputType.JS_DPAD, 0, JoystickConstants.DPAD_X, true),
   DRV_BUTTON_1("Driver button 1", WSInputType.JS_BUTTON, 0, 0, true),
   DRV_BUTTON_2("Driver button 2", WSInputType.JS_BUTTON, 0, 1, true),
   DRV_BUTTON_3("Driver button 3", WSInputType.JS_BUTTON, 0, 2, true),
   DRV_BUTTON_4("Driver button 4", WSInputType.JS_BUTTON, 0, 3, true),
   DRV_BUTTON_5("Driver button 5", WSInputType.JS_BUTTON, 0, 4, true),
   DRV_BUTTON_6("Driver button 6", WSInputType.JS_BUTTON, 0, 5, true),
   DRV_BUTTON_7("Driver button 7", WSInputType.JS_BUTTON, 0, 6, true),
   DRV_BUTTON_8("Driver button 8", WSInputType.JS_BUTTON, 0, 7, true),
   DRV_BUTTON_9("Driver button 9", WSInputType.JS_BUTTON, 0, 8, true),
   DRV_BUTTON_10("Driver button 10", WSInputType.JS_BUTTON, 0, 9, true),
   DRV_BUTTON_11("Driver button 11", WSInputType.JS_BUTTON, 0, 10, true),
   DRV_BUTTON_12("Driver button 12", WSInputType.JS_BUTTON, 0, 11, true),

   // Manipulator Enums
   MAN_RIGHT_JOYSTICK_Y("MANIPULATOR_BACK_ARM_CONTROL", WSInputType.JS_JOYSTICK, 1, JoystickConstants.RIGHT_JOYSTICK_Y, true),
   MAN_RIGHT_JOYSTICK_X("MANIPULATOR_RIGHT_JOYSTICK_X", WSInputType.JS_JOYSTICK, 1, JoystickConstants.RIGHT_JOYSTICK_X, true),
   MAN_LEFT_JOYSTICK_X("MANIPULATOR_LEFT_JOYSTICK_X", WSInputType.JS_JOYSTICK, 1, JoystickConstants.LEFT_JOYSTICK_X, true),
   MAN_DPAD_Y("Manipulator DPad Y", WSInputType.JS_DPAD, 1, JoystickConstants.DPAD_Y, true),
   MAN_DPAD_X("Manipulator DPad X", WSInputType.JS_DPAD, 1, JoystickConstants.DPAD_X, true),
   MAN_BUTTON_1("Manipulator button 1", WSInputType.JS_BUTTON, 1, 0, true),
   MAN_BUTTON_2("Manipulator button 2", WSInputType.JS_BUTTON, 1, 1, true),
   MAN_BUTTON_3("Manipulator button 3", WSInputType.JS_BUTTON, 1, 2, true),
   MAN_BUTTON_4("Manipulator button 4", WSInputType.JS_BUTTON, 1, 3, true),
   MAN_BUTTON_5("Manipulator button 5", WSInputType.JS_BUTTON, 1, 4, true),
   MAN_BUTTON_6("Manipulator button 6", WSInputType.JS_BUTTON, 1, 5, true),
   MAN_BUTTON_7("Manipulator button 7", WSInputType.JS_BUTTON, 1, 6, true),
   MAN_BUTTON_8("Manipulator button 8", WSInputType.JS_BUTTON, 1, 7, true),
   MAN_BUTTON_9("Manipulator button 9", WSInputType.JS_BUTTON, 1, 8, true),
   MAN_BUTTON_10("Manipulator button 10", WSInputType.JS_BUTTON, 1, 9, true),
   MAN_BUTTON_11("Manipulator button 11", WSInputType.JS_BUTTON, 1, 10, true),
   MAN_BUTTON_12("Manipulator button 12", WSInputType.JS_BUTTON, 1, 11, true),
   
   HALL_EFFECT("Lift hall effect sensors", WSInputType.HALL_EFFECT, Port.kMXP, 0x10, true),
   START_POSITION_SELECTOR("Start position selector", WSInputType.POT, 18, true),
   AUTO_PROGRAM_SELECTOR("Auto program selector", WSInputType.POT, 17, true),
   LOCK_IN_SWITCH("OI Lock in switch", WSInputType.SWITCH, 1, true, true),
   LIFT_POT("Lift Pot", WSInputType.POT, 0, true);
   

   private final String m_name;
   private final InputType m_type;
   private final Object m_port;
   private Object m_module;
   private Object m_default;
   private boolean m_trackingState;
   private boolean m_pullup;
   
   WSInputs(String p_name, InputType p_type, Object p_module, Object p_port, boolean p_trackingState)
   {
      m_name = p_name;
      m_type = p_type;
      m_port = p_port;
      m_module = p_module;
      m_trackingState = p_trackingState;
   }
   
   WSInputs(String p_name, InputType p_type, int p_port, boolean p_trackingState, boolean p_pullup)
   {
      m_name = p_name;
      m_type = p_type;
      m_port = p_port;
      m_trackingState = p_trackingState;
      m_pullup = p_pullup;
   }
   
   WSInputs(String p_name, InputType p_type, int p_port, boolean p_default, boolean p_trackingState, boolean p_pullup)
   {
      m_name = p_name;
      m_type = p_type;
      m_port = p_port;
      m_default = p_default;
      m_trackingState = p_trackingState;
      m_pullup = p_pullup;
   }
   
   WSInputs(String p_name, InputType p_type, int p_port, double p_default, boolean p_trackingState)
   {
      m_name = p_name;
      m_type = p_type;
      m_port = p_port;
      m_default = p_default;
      m_trackingState = p_trackingState;
   }
   
   WSInputs(String p_name, InputType p_type, int p_port, boolean p_trackingState)
   {
      m_name = p_name;
      m_type = p_type;
      m_port = p_port;
      m_trackingState = p_trackingState;
   }
   
   @Override
   public String getName()
   {
      return m_name;
   }
   
   @Override
   public InputType getType()
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
   
   public boolean getPullup()
   {
      return m_pullup;
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

   
}