package github.robotters.rewrite.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import github.robotters.rewrite.Constants;

public class Arm extends SubsystemBase {
    // Have the arm handle it's own PID Controller State For Now
    // TODO: FIGURE OUT IF THIS IS A GOOD MOVE, OR SHOULD WE Restart Counting On Auto End?
    public final PIDController mPidController =
            new PIDController(Constants.ArmKp, Constants.ArmKi, Constants.ArmKd);
    private final DcMotor mMotor;
    public ArmPosition armState;

    public Arm(HardwareMap hwMap) {
        mMotor = hwMap.get(DcMotor.class, Constants.ArmMotorKey);
        mMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armState = ArmPosition.DOWN;
    }

    public void RunToPower(double power) {
        mMotor.setPower(power);
    }

    public double GetArmPosition() {
        return mMotor.getCurrentPosition();
    }

    // The Arm Position Enum Provides Target Arm Positions that Can Later Be Selected
    public enum ArmPosition {
        DOWN(0.0),
        UP(3.0);

        public final double position;

        ArmPosition(double position) {
            this.position = position;
        }
    }

    public static class ArmDefaultRunCommand extends CommandBase {
        private final GamepadEx mGamepad;
        private final Arm mArm;

        public ArmDefaultRunCommand(Arm arm, GamepadEx gamepad) {
            this.mGamepad = gamepad;
            this.mArm = arm;

            addRequirements(arm);
        }

        @Override
        public void execute() {
            setState();
            runArmToPosition();
        }

        public void setState() {
            boolean up = mGamepad.isDown(Constants.ArmUpPositionBinding),
                    down = mGamepad.isDown(Constants.ArmDownPositionBinding);
            if (up && down) {
                return;
            }

            if (up) {
                mArm.armState = ArmPosition.UP;
            }

            if (down) {
                mArm.armState = ArmPosition.DOWN;
            }
        }

        public void runArmToPosition() {
            mArm.RunToPower(
                    mArm.mPidController.calculate(mArm.GetArmPosition(), mArm.armState.position));
        }
    }
}
