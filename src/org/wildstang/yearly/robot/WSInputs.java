package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Inputs;
import org.wildstang.framework.hardware.InputConfig;
import org.wildstang.framework.io.inputs.InputType;
import org.wildstang.hardware.JoystickConstants;
import org.wildstang.hardware.crio.inputs.WSInputType;
import org.wildstang.hardware.crio.inputs.config.WsDigitalInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsI2CInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsJSButtonInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsJSJoystickInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsMotionProfileConfig;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public enum WSInputs implements Inputs {
    // im.addSensorInput(LIDAR, new WsLIDAR());
    //

    DRV_THROTTLE("Driver throttle", WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.LEFT_JOYSTICK_Y), true), 
    DRV_HEADING("Driver heading", WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.RIGHT_JOYSTICK_X), true), 
    DRV_LEFT_X("Driver left X", WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.LEFT_JOYSTICK_X), true), 
    DRV_RIGHT_Y("Driver right Y", WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.RIGHT_JOYSTICK_Y), true),
    //DRV_BUTTON_1("Driver button 1", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 0), true), 
    DRV_BUTTON_2("Driver Limbo", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 1), true), 
    //DRV_BUTTON_3("Driver button 3", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 2), true), 
    //DRV_BUTTON_4("Driver button 4", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 3), true), 
    DRV_BUTTON_5("Driver Turret Mode", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 4), true), 
    DRV_BUTTON_6("Driver Shift", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 5), true), 
    DRV_BUTTON_7("Driver Intake Nose Control", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 6), true), 
    DRV_BUTTON_8("Driver Turbo", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 7), true),
							    
     // This should get deleted, for debug only
   DRV_BUTTON_12("Antiturbo (Driver 8)", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 11), true),

    // Manipulator Enums
    MAN_RIGHT_JOYSTICK_Y("Manip Climb up_down", WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.RIGHT_JOYSTICK_Y), true), 
    //MAN_RIGHT_JOYSTICK_X("MANIPULATOR_RIGHT_JOYSTICK_X", WSInputType.JS_JOYSTICK,new WsJSJoystickInputConfig(1, JoystickConstants.RIGHT_JOYSTICK_X), true), 
    //MAN_LEFT_JOYSTICK_X("MANIPULATOR_LEFT_JOYSTICK_X", WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.LEFT_JOYSTICK_X), true), 
    MAN_LEFT_JOYSTICK_Y("Manip Intake in_out", WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.LEFT_JOYSTICK_Y), true),
    MAN_BUTTON_1("Manip Deploy Climber Arm", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 0), true), 
    MAN_BUTTON_2("Manip Deploy Climber Hook", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 1),true), 
    MAN_BUTTON_3("Manip Flywheel on_off", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 2), true), 
    MAN_BUTTON_4("Manip Shot Distance", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 3), true),
    MAN_BUTTON_5("Manip Intake Nose", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 4), true), 
    MAN_BUTTON_6("Manip Shooter Hood", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 5), true), 
    MAN_BUTTON_7("Manip Deploy Intake", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 6), true), 
    MAN_BUTTON_8("Manip Shoot", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 7), true), 
    MAN_BUTTON_9("Override", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 8), true), 
    //MAN_BUTTON_10("Manipulator button 10", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 9), true), 
    //MAN_BUTTON_11("Manipulator button 11", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 10), true), 
    //MAN_BUTTON_12("Manipulator button 12", WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 11), true),
    HALL_EFFECT("Lift hall effect sensors", WSInputType.HALL_EFFECT, new WsI2CInputConfig(Port.kMXP, 0x10), true),
    // LIMIT_SWITCH("Limit switch", WSInputType.SWITCH, 0, true),
    //POT("Pot", WSInputType.POT, new WsAnalogInputConfig(0), true), 
    IMU("IMU", WSInputType.I2C, new WsI2CInputConfig(I2C.Port.kOnboard, 0x20), true), 
    MOTION_PROFILE_CONTROL("MotionProfileConfig", WSInputType.MOTION_PROFILE_CONTROL, new WsMotionProfileConfig(), false), 
    INTAKE_BOLDER_SENSOR("Intake Ball Staging", WSInputType.SWITCH, new WsDigitalInputConfig(8, false), true),
    INTAKE_BALL_DETECT("Intake ball detection", WSInputType.SWITCH, new WsDigitalInputConfig(9, false), true),
    RIGHT_ARM_TOUCHING("Right Lift arm touching", WSInputType.SWITCH, new WsDigitalInputConfig(7, false), true),
    LEFT_ARM_TOUCHING("Left Lift arm touching", WSInputType.SWITCH, new WsDigitalInputConfig(6, false), true);
    //LEFT_DRIVE_ENCODER
    //RIGHT_DRIVER_ENCODER
    //SHOOTER_ENCODER
    // DIO_0_INTAKE_SENSOR("intake sensor", WSInputType.SWITCH, new
    // WsDigitalInputConfig(6, true), true);

    private final String m_name;
    private final InputType m_type;

    private InputConfig m_config = null;

    private boolean m_trackingState;

    WSInputs(String p_name, InputType p_type, InputConfig p_config, boolean p_trackingState) {
	m_name = p_name;
	m_type = p_type;
	m_config = p_config;
	m_trackingState = p_trackingState;
    }

    @Override
    public String getName() {
	return m_name;
    }

    @Override
    public InputType getType() {
	return m_type;
    }

    public InputConfig getConfig() {
	return m_config;
    }

    public boolean isTrackingState() {
	return m_trackingState;
    }

}
