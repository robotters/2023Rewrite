package github.robotters.rewrite.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import github.robotters.rewrite.Constants;

public class Claw extends SubsystemBase {
    // Please Ensure Servo is not CR
    private final Servo mServo;

    // Claw State
    public ClawState mClawState;

    public Claw(HardwareMap hwmap) {
        mServo = hwmap.get(Servo.class, Constants.ClawServoKey);
        mClawState = ClawState.CLOSED;
    }

    public void SetPosition(double position) {
        mServo.setPosition(position);
    }

    // Current Claw State
    enum ClawState {
        OPEN(1.0),
        CLOSED(0.0);

        public final double position;

        ClawState(double pos) {
            position = pos;
        }
    }

    public static class DefaultClawCommand extends CommandBase {
        private final GamepadEx mGamepad;
        private final Claw mClaw;

        public DefaultClawCommand(Claw claw, GamepadEx gamepad) {
            this.mGamepad = gamepad;
            this.mClaw = claw;

            addRequirements(mClaw);
        }

        @Override
        public void execute() {
            handleGamepad();
            moveClaw();
        }

        private void handleGamepad() {
            if (!mGamepad.isDown(Constants.ClawOpenCloseBinding)) {
                return;
            }

            switch (mClaw.mClawState) {
                case OPEN:
                    mClaw.mClawState = ClawState.CLOSED;
                    break;
                case CLOSED:
                    mClaw.mClawState = ClawState.OPEN;
                    break;
                default:
                    break;
            }
        }

        private void moveClaw() {
            mClaw.SetPosition(mClaw.mClawState.position);
        }
    }
}
