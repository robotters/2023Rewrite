package github.robotters.rewrite;

import com.qualcomm.robotcore.hardware.HardwareMap;

import github.robotters.rewrite.util.ImuHandler;

/*
The `RobotProps` Class Provides an Interface to Pass Op Mode Initialization Values To the Robot
Class.
*/
public class RobotProps {
    public HardwareMap mHardwaremap;

    public ImuHandler imuHandler;

    public RobotProps(HardwareMap hwMap, ImuHandler imuHandler) {
        this.mHardwaremap = hwMap;
        this.imuHandler = imuHandler;
    }
}
