package github.robotters.rewrite.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import github.robotters.rewrite.Constants;

public class DriveTrain extends SubsystemBase {
    private final MecanumDrive mDriveBase;

    // Initialize DriveTrain
    public DriveTrain(HardwareMap hwMap) {
        this.mDriveBase =
                new MecanumDrive(
                        new Motor(hwMap, Constants.LeftFrontKey, Motor.GoBILDA.RPM_312),
                        new Motor(hwMap, Constants.RightFrontKey, Motor.GoBILDA.RPM_312),
                        new Motor(hwMap, Constants.LeftBackKey, Motor.GoBILDA.RPM_312),
                        new Motor(hwMap, Constants.RightBackKey, Motor.GoBILDA.RPM_312));
    }

    public void DriveRobotOriented(double x, double y, double r) {
        mDriveBase.driveRobotCentric(x, y, r);
    }

    public static class DefaultDriveCommand extends CommandBase {
        private final DriveTrain mDriveTrain;
        private final GamepadEx mGamepad;
        private DriveOrientation currentDriveOrientation = DriveOrientation.ROBOT;

        public DefaultDriveCommand(DriveTrain driveTrain, GamepadEx gamepad) {
            this.mDriveTrain = driveTrain;
            this.mGamepad = gamepad;

            addRequirements(driveTrain);
        }

        @Override
        public void execute() {
            handleGamepad();
            handleDriving();
        }

        // TODO: Add Field Oriented Driving + BREAK IN SWITCH STATEMENT
        private void handleDriving() {
            switch (currentDriveOrientation) {
                case FIELD:
                case ROBOT:
                    this.mDriveTrain.DriveRobotOriented(
                            mGamepad.getLeftX(), mGamepad.getLeftY(), mGamepad.getRightX());
                    break;
            }
        }

        // Simple Interal Switching Logic
        private void handleGamepad() {
            if (!mGamepad.isDown(Constants.DriveBaseModeSwitchButton)) {
                return;
            }

            switch (currentDriveOrientation) {
                case FIELD:
                    currentDriveOrientation = DriveOrientation.ROBOT;
                    break;
                case ROBOT:
                    currentDriveOrientation = DriveOrientation.FIELD;
                    break;
            }
        }

        enum DriveOrientation {
            FIELD,
            ROBOT
        }
    }
}
