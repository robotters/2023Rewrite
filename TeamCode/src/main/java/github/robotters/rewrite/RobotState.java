package github.robotters.rewrite;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotState {
    private static RobotState mInstance;
    private Double ImuAngle;

    // Get the Singleton Instance
    public static RobotState getInstance() {
        if (mInstance == null) {
            mInstance = new RobotState();
        }

        return mInstance;
    }

    public synchronized void SetImuAngle(Double angle) {
        this.ImuAngle = angle;
    }

    public Double GetImuAngle() {
        return this.ImuAngle;
    }

    // Add the State As Fields into the Telemetry instance
    public void outputtoTelemetry(Telemetry telemetry) {
        telemetry.addData("IMU ANGLE", this.ImuAngle);
    }
}
