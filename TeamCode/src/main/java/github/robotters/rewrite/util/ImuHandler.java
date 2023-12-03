package github.robotters.rewrite.util;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import github.robotters.rewrite.Constants;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class ImuHandler {
    private final IMU mImu;
    private final double angleOffset;
    private double headingDegrees = 0.0;

    // Get and Initialize IMU
    public ImuHandler(HardwareMap hwMap, double angleOffset) {
        this.angleOffset = angleOffset;
        // Device name is not in Constants.java, since it never changes!
        mImu = hwMap.get(IMU.class, "imu");
        mImu.initialize(Constants.RobotImuParameters);
        mImu.resetYaw();
    }

    public void imuLoop() {
        headingDegrees = mImu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + angleOffset;
    }

    public double getDegreessYaw() {
        return headingDegrees;
    }

    public void resetYaw() {
        mImu.resetYaw();
    }
}
