package github.robotters.rewrite;

import com.arcrobotics.ftclib.command.InstantCommand;

import github.robotters.rewrite.autoutil.AutoSequence;
import github.robotters.rewrite.subsystems.AirplaneLauncher;
import github.robotters.rewrite.subsystems.Arm;
import github.robotters.rewrite.subsystems.Claw;
import github.robotters.rewrite.subsystems.DriveTrain;
import github.robotters.rewrite.subsystems.ElementDetector;

// The Robot Class Represents the Robot, with it's subsystems and commands.
public class Robot extends com.arcrobotics.ftclib.command.Robot {
    // RobotProps State Class Constructor
    public final RobotProps mRobotProps;

    // Subsystems (Don't Initialize)
    public final DriveTrain mDriveTrain;
    public final AirplaneLauncher mAirplaneLauncher;
    public final Arm mArm;
    public final Claw mClaw;
    public final ElementDetector mElementDetector;

    public Robot(RobotProps props) {
        this.mRobotProps = props;
        mRobotProps.logger.init(this);

        // Subsystem Initialization
        mDriveTrain = new DriveTrain(props.mHardwaremap);
        mAirplaneLauncher = new AirplaneLauncher(props.mHardwaremap);
        mArm = new Arm(props.mHardwaremap);
        mClaw = new Claw(props.mHardwaremap);
        mElementDetector = new ElementDetector(props.mHardwaremap, props.color);

        register(mDriveTrain, mAirplaneLauncher, mArm, mClaw, mElementDetector);
    }

    public void teleopInit(TeleopProps props) {
        // Set Default Drive Command to Default RobotCentricDriveCommand
        mDriveTrain.setDefaultCommand(
                new DriveTrain.DefaultDriveCommand(
                        mDriveTrain, props.gamepad1, mRobotProps.imuHandler));

        // Set Arm Default Run Command
        mArm.setDefaultCommand(new Arm.ArmDefaultRunCommand(mArm, props.gamepad1));

        // Set Claw Default Run Command
        mClaw.setDefaultCommand(new Claw.DefaultClawCommand(mClaw, props.gamepad1));

        // Launch Airplane When Button Is Pressed
        props.gamepad1
                .getGamepadButton(Constants.AirplaneLauncherLaunchButton)
                .whenPressed(new InstantCommand(mAirplaneLauncher::Launch, mAirplaneLauncher));

        // Reset Yaw When Button is Pressed
        props.gamepad1
                .getGamepadButton(Constants.IMUPoseResetButton)
                .whenReleased(new InstantCommand(mRobotProps.imuHandler::resetYaw));
    }

    @Override
    public void run() {
        mRobotProps.bulkReader.bulkRead();
        mRobotProps.imuHandler.imuLoop();
        super.run();
        mRobotProps.logger.Log();
    }

    public void autoInit(AutoSequence sequence) {
        // Schedule the Provided init Sequence
        schedule(sequence.init());
    }

    public void autoRun(AutoSequence sequence) {
        // Schedule the provided run sequence
        schedule(sequence.main());
    }
}
