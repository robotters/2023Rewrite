package github.robotters.rewrite.opmodes;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import github.robotters.rewrite.Constants;
import github.robotters.rewrite.Robot;
import github.robotters.rewrite.RobotProps;
import github.robotters.rewrite.autoutil.AutoSequence;
import github.robotters.rewrite.subsystems.AutoParkCommand;
import github.robotters.rewrite.util.BulkReader;
import github.robotters.rewrite.util.ImuHandler;
import github.robotters.rewrite.util.RobotHolder;
import github.robotters.rewrite.util.RobotStateLogger;

@Autonomous
public class Auto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ImuHandler imuHandler = new ImuHandler(hardwareMap, 0.0);

        // Update the Driver Station with Telemetry every N Milliseconds
        telemetry.setMsTransmissionInterval(Constants.TelemetrySendInterval);

        RobotStateLogger logger = new RobotStateLogger(telemetry);

        BulkReader bulkReader = new BulkReader(hardwareMap);

        RobotProps props = new RobotProps(hardwareMap, imuHandler, bulkReader, logger, RobotProps.Color.BLUE);
        Robot r = RobotHolder.getRobot(props);
        AutoSequence autoSequence = new AutoSequence() {
            @Override
            public SequentialCommandGroup init() {
                return new SequentialCommandGroup();
            }

            @Override
            public SequentialCommandGroup main() {
                return new SequentialCommandGroup(new AutoParkCommand(r.mDriveTrain));
            }
        };
        telemetry.addData("STATUS", "WAITING");
        telemetry.update();
        r.autoInit(autoSequence);
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            // Bulk Read All Hubs
            bulkReader.bulkRead();
            // Run IMU Periodic
            imuHandler.imuLoop();
            r.autoRun(autoSequence);
            telemetry.update();
        }

        r.reset();
    }
}
