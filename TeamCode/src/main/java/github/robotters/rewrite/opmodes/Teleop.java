package github.robotters.rewrite.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import github.robotters.rewrite.Robot;
import github.robotters.rewrite.RobotProps;
import github.robotters.rewrite.util.ImuHandler;

@TeleOp
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ImuHandler imuHandler = new ImuHandler(hardwareMap);
        RobotProps props = new RobotProps(hardwareMap, gamepad1, imuHandler);

        Robot r = new Robot(props);

        r.teleopInit();
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            // Run IMU Periodic
            imuHandler.imuLoop();
            r.run();
        }

        r.reset();
    }
}
