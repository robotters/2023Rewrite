package github.robotters.rewrite.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import github.robotters.rewrite.Constants;

@TeleOp
public class EncoderTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.setMsTransmissionInterval(20);

        DcMotor armMotor = hardwareMap.get(DcMotor.class, Constants.ArmMotorKey);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetry.addData("READY", "TRUE");
        telemetry.update();
        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            telemetry.addData("ARM POS", armMotor.getCurrentPosition());
            telemetry.update();
        }
    }
}
