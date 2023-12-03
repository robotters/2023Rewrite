package github.robotters.rewrite;

import com.arcrobotics.ftclib.command.InstantCommand;

import github.robotters.rewrite.subsystems.AirplaneLauncher;
import github.robotters.rewrite.subsystems.Arm;
import github.robotters.rewrite.subsystems.Claw;
import github.robotters.rewrite.subsystems.DriveTrain;

// The Robot Class Represents the Robot, with it's subsystems and commands.
public class Robot extends com.arcrobotics.ftclib.command.Robot {
    // RobotProps State Class Constructor
    public final RobotProps mRobotProps;

    // Subsystems (Don't Initialize)
    public final DriveTrain mDriveTrain;
    public final AirplaneLauncher mAirplaneLauncher;
    public final Arm mArm;
    public final Claw mClaw;

    public Robot(RobotProps props) {
        this.mRobotProps = props;

        // Subsystem Initialization
        mDriveTrain = new DriveTrain(props.mHardwaremap);
        mAirplaneLauncher = new AirplaneLauncher(props.mHardwaremap);
        mArm = new Arm(props.mHardwaremap);
        mClaw = new Claw(props.mHardwaremap);

        register(mDriveTrain, mAirplaneLauncher, mArm, mClaw);
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

    public void autoInit() {}
}
