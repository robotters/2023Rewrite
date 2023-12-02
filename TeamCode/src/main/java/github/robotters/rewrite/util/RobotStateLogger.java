package github.robotters.rewrite.util;

import github.robotters.rewrite.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// Robot State Logger Provides an Interface For the Logging of Robot States + Poses
public class RobotStateLogger {
    private final Robot mRobot;

    public RobotStateLogger(Robot mRobot) {
        this.mRobot = mRobot;
    }

    // Logs Robot States Onto the Provided Telemetry Object
    public void Log(Telemetry telemetry) {
        telemetry.addData("CLAW STATE", mRobot.mClaw.mClawState);
        telemetry.addData("ARM TARGET POSITION", mRobot.mArm.armState);
        telemetry.addData("DRIVETRAIN MODE", mRobot.mDriveTrain.currentDriveOrientation);
    }
}
