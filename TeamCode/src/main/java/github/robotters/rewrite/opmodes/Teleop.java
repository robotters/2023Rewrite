package github.robotters.rewrite.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import github.robotters.rewrite.Robot;
import github.robotters.rewrite.RobotProps;
import github.robotters.rewrite.RobotState;

@TeleOp
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RobotProps props = new RobotProps(hardwareMap, gamepad1);

        Robot r = new Robot(props);
        r.teleopInit();
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            r.run();
            RobotState.getInstance().outputtoTelemetry(telemetry);
            telemetry.update();
        }

        r.reset();
    }
}
