package github.robotters.rewrite;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.logging.Logger;

import github.robotters.rewrite.util.BulkReader;
import github.robotters.rewrite.util.ImuHandler;
import github.robotters.rewrite.util.RobotStateLogger;

/*
The `RobotProps` Class Provides an Interface to Pass Op Mode Initialization Values To the Robot
Class.
*/
public class RobotProps {
    public HardwareMap mHardwaremap;

    public ImuHandler imuHandler;

    public RobotStateLogger logger;

    public BulkReader bulkReader;

    public RobotProps(
            HardwareMap hwMap,
            ImuHandler imuHandler,
            BulkReader bulkReader,
            RobotStateLogger logger) {
        this.mHardwaremap = hwMap;
        this.imuHandler = imuHandler;
        this.bulkReader = bulkReader;
        this.logger = logger;
    }
}
