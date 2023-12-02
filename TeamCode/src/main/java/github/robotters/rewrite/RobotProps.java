package github.robotters.rewrite;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import github.robotters.rewrite.util.ImuHandler;

/*
The `RobotProps` Class Provides an Interface to Pass Op Mode Initialization Values To the Robot
Class.
*/
public class RobotProps {
    public HardwareMap mHardwaremap;
    public GamepadEx gamepad1;

    public ImuHandler imuHandler;

    public RobotProps(HardwareMap hwMap, Gamepad gamepad1, ImuHandler imuHandler) {
        this.mHardwaremap = hwMap;
        this.gamepad1 = new GamepadEx(gamepad1);
        this.imuHandler = imuHandler;
    }
}
