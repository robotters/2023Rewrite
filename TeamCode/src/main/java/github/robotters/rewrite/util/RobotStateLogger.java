package github.robotters.rewrite.util;

import github.robotters.rewrite.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// Robot State Logger Provides an Interface For the Logging of Robot States + Poses
public class RobotStateLogger {
    private final Telemetry telemetry;
    private Robot mRobot;

    public RobotStateLogger(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void init(Robot r) {
        this.mRobot = r;
    }

    // Logs Robot States Onto the Provided Telemetry Object
    public void Log() {
        telemetry.addData("CLAW STATE", mRobot.mClaw.mClawState);
        telemetry.addData("ARM TARGET POSITION", mRobot.mArm.armState);
        telemetry.addData("DRIVETRAIN MODE", mRobot.mDriveTrain.currentDriveOrientation);
        telemetry.addData("ARM CURRENT", mRobot.mArm.GetArmPosition());
    }
}
