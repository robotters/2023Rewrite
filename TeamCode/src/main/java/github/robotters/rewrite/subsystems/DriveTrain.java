package github.robotters.rewrite.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import github.robotters.rewrite.Constants;
import github.robotters.rewrite.util.ImuHandler;

public class DriveTrain extends SubsystemBase {
    private final MecanumDrive mDriveBase;

    public DefaultDriveCommand.DriveOrientation currentDriveOrientation =
            DefaultDriveCommand.DriveOrientation.FIELD;

    public CoefficientsStatus coefficientsStatus = CoefficientsStatus.NORMAL;

    private DriveTrainCoefficients coefficients = new DriveTrainCoefficients(Constants.DriveTrainCoefficients.x, Constants.DriveTrainCoefficients.y, Constants.DriveTrainCoefficients.z);

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

    public void DriveFieldOriented(double x, double y, double r, double heading) {
        mDriveBase.driveFieldCentric(x, y, r, heading);
    }

    public void HalfDriveCoefficients() {
        switch (coefficientsStatus) {
            case NORMAL:
                coefficients.x = Constants.DriveTrainCoefficients.x * 0.5;
                coefficients.y = Constants.DriveTrainCoefficients.y * 0.5;
                coefficients.z = Constants.DriveTrainCoefficients.z * 0.5;
                coefficientsStatus = CoefficientsStatus.HALVED;
                break;
            case HALVED:
                coefficients.x = Constants.DriveTrainCoefficients.x;
                coefficients.y = Constants.DriveTrainCoefficients.y;
                coefficients.z = Constants.DriveTrainCoefficients.z;
                coefficientsStatus = CoefficientsStatus.NORMAL;
                break;
        }
    }

    public enum CoefficientsStatus {
        NORMAL,
        HALVED
    }

    public static class DriveTrainCoefficients {
        double x, y, z;
        public DriveTrainCoefficients(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static class DefaultDriveCommand extends CommandBase {
        private final DriveTrain mDriveTrain;
        private final GamepadEx mGamepad;
        private final ImuHandler mImu;

        public DefaultDriveCommand(
                DriveTrain driveTrain, GamepadEx gamepad, ImuHandler imuHandler) {
            this.mDriveTrain = driveTrain;
            this.mGamepad = gamepad;
            this.mImu = imuHandler;

            addRequirements(driveTrain);
        }

        @Override
        public void execute() {
            handleGamepad();
            handleDriving();
        }

        private void handleDriving() {
            switch (mDriveTrain.currentDriveOrientation) {
                case FIELD:
                    this.mDriveTrain.DriveFieldOriented(
                            mGamepad.getLeftX() * mDriveTrain.coefficients.x,
                            mGamepad.getLeftY() * mDriveTrain.coefficients.y,
                            mGamepad.getRightX() * mDriveTrain.coefficients.z,
                            mImu.getDegreessYaw());
                    break;
                case ROBOT:
                    this.mDriveTrain.DriveRobotOriented(
                            mGamepad.getLeftX() * mDriveTrain.coefficients.x, mGamepad.getLeftY() * mDriveTrain.coefficients.y, -mGamepad.getRightX() * mDriveTrain.coefficients.z);
                    break;
            }
        }

        // Simple Interal Switching Logic
        private void handleGamepad() {
            if (!mGamepad.isDown(Constants.DriveBaseModeSwitchButton)) {
                return;
            }

            switch (mDriveTrain.currentDriveOrientation) {
                case FIELD:
                    mDriveTrain.currentDriveOrientation = DriveOrientation.ROBOT;
                    break;
                case ROBOT:
                    mDriveTrain.currentDriveOrientation = DriveOrientation.FIELD;
                    break;
            }
        }

        enum DriveOrientation {
            FIELD,
            ROBOT
        }
    }
}
