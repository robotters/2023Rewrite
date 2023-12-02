package github.robotters.rewrite;

import com.arcrobotics.ftclib.command.InstantCommand;

import github.robotters.rewrite.subsystems.AirplaneLauncher;
import github.robotters.rewrite.subsystems.Arm;
import github.robotters.rewrite.subsystems.DriveTrain;

// The Robot Class Represents the Robot, with it's subsystems and commands.
public class Robot extends com.arcrobotics.ftclib.command.Robot {
    // RobotProps State Class Constructor
    public final RobotProps mRobotProps;

    // Subsystems (Don't Initialize)
    private final DriveTrain mDriveTrain;
    private final AirplaneLauncher mAirplaneLauncher;
    private final Arm mArm;

    public Robot(RobotProps props) {
        this.mRobotProps = props;

        // Subsystem Initialization
        mDriveTrain = new DriveTrain(props.mHardwaremap);
        mAirplaneLauncher = new AirplaneLauncher(props.mHardwaremap);
        mArm = new Arm(props.mHardwaremap);

        register(mDriveTrain);
    }

    public void teleopInit() {
        // Set Default Drive Command to Default RobotCentricDriveCommand
        mDriveTrain.setDefaultCommand(
                new DriveTrain.DefaultDriveCommand(mDriveTrain, mRobotProps.gamepad1));

        // Set Arm Default Run Command
        mArm.setDefaultCommand(new Arm.ArmDefaultRunCommand(mArm, mRobotProps.gamepad1));

        // Launch Airplane When Button Is Pressed
        mRobotProps
                .gamepad1
                .getGamepadButton(Constants.AirplaneLauncherLaunchButton)
                .whenPressed(new InstantCommand(mAirplaneLauncher::Launch, mAirplaneLauncher));
    }

    public void autoInit() {}
}
