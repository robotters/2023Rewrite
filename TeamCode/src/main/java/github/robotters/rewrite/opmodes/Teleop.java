package github.robotters.rewrite.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import github.robotters.rewrite.Constants;
import github.robotters.rewrite.Robot;
import github.robotters.rewrite.RobotProps;
import github.robotters.rewrite.util.ImuHandler;
import github.robotters.rewrite.util.RobotStateLogger;

@TeleOp
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ImuHandler imuHandler = new ImuHandler(hardwareMap);
        RobotProps props = new RobotProps(hardwareMap, gamepad1, imuHandler);

        // Update the Driver Station with Telemetry every N Milliseconds
        telemetry.setMsTransmissionInterval(Constants.TelemetrySendInterval);

        Robot r = new Robot(props);

        RobotStateLogger logger = new RobotStateLogger(r);

        r.teleopInit();
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            // Run IMU Periodic
            imuHandler.imuLoop();
            r.run();
            logger.Log(telemetry);
            telemetry.update();
        }

        r.reset();
    }
}
