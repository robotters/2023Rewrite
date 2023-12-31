package github.robotters.rewrite.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import github.robotters.rewrite.Constants;

public class Intake extends SubsystemBase {
    private final DcMotor mMotor;
    private IntakePosition position = IntakePosition.STOPPED;

    public Intake(HardwareMap hwMap) {
        mMotor = hwMap.get(DcMotor.class, Constants.IntakeKey);
    }

    public enum IntakePosition {
        IN(-1.0),
        STOPPED(0.0),
        OUT(1.0);

        public final double power;

        IntakePosition(double power) {
            this.power = power;
        }
    }

    public void setPower(double power) { mMotor.setPower(power);}

    public void setPosition(IntakePosition position) {
        this.position = position;
    }

    public static class DefaultIntakeCommand extends CommandBase {
        private final Intake mIntake;
        private final GamepadEx mGamepadEx;

        public DefaultIntakeCommand(Intake intake, GamepadEx mGamepadEx) {
            this.mIntake = intake;
            this.mGamepadEx = mGamepadEx;
            addRequirements(mIntake);
        }

        @Override
        public void execute() {
            setState();
            mIntake.setPower(mIntake.position.power);
        }

        private void setState() {
            if (mGamepadEx.isDown(Constants.IntakeInBinding) == mGamepadEx.isDown(Constants.IntakeOutBinding)) {
                mIntake.position = IntakePosition.STOPPED;
            } else if (mGamepadEx.isDown(Constants.IntakeInBinding)) {
                mIntake.position = IntakePosition.IN;
            } else if (mGamepadEx.isDown(Constants.IntakeOutBinding)) {
                mIntake.position = IntakePosition.OUT;
            }
        }
    }
}
