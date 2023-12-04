package github.robotters.rewrite.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import github.robotters.rewrite.Constants;
import github.robotters.rewrite.Robot;
import github.robotters.rewrite.RobotProps;
import github.robotters.rewrite.TeleopProps;
import github.robotters.rewrite.util.BulkReader;
import github.robotters.rewrite.util.ImuHandler;
import github.robotters.rewrite.util.RobotHolder;
import github.robotters.rewrite.util.RobotStateLogger;

@TeleOp
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ImuHandler imuHandler = new ImuHandler(hardwareMap, 0.0);

        // Update the Driver Station with Telemetry every N Milliseconds
        telemetry.setMsTransmissionInterval(Constants.TelemetrySendInterval);

        RobotStateLogger logger = new RobotStateLogger(telemetry);

        BulkReader bulkReader = new BulkReader(hardwareMap);

        RobotProps props = new RobotProps(hardwareMap, imuHandler, bulkReader, logger);
        Robot r = RobotHolder.getRobot(props);
        telemetry.addData("STATUS", "WAITING");
        telemetry.update();
        r.teleopInit(new TeleopProps(gamepad1));
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            // Bulk Read All Hubs
            bulkReader.bulkRead();
            // Run IMU Periodic
            imuHandler.imuLoop();
            r.run();
            telemetry.update();
        }

        r.reset();
    }
}
