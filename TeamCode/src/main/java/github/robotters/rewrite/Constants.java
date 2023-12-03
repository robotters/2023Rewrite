package github.robotters.rewrite;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;

public class Constants {
    public static int TelemetrySendInterval = 200;

    // DriveBase Info
    public static String LeftFrontKey = "left-front",
            RightFrontKey = "right-front",
            LeftBackKey = "left back",
            RightBackKey = "right-back";

    // Airplane Launcher Info
    public static String AirplaneLauncherServoKey = "airplane-servo";
    public static double AirplaneLauncherStartPos = 0.0, AirplaneLauncherEndPos = 1.0;

    // CLAW INFO:
    // PLEASE MAKE THIS NON-CR
    public static String ClawServoKey = "claw-servo";

    // ARM INFO:
    public static String ArmMotorKey = "arm-motor";

    // IMU Parameters (Needed For Correct IMU Initialization)
    public static IMU.Parameters RobotImuParameters =
            new IMU.Parameters(
                    new RevHubOrientationOnRobot(
                            RevHubOrientationOnRobot.LogoFacingDirection.UP,
                            RevHubOrientationOnRobot.UsbFacingDirection.RIGHT));
    public static double ArmKp = 1.0, ArmKi = 0.0, ArmKd = 0.0;

    // GAME-PAD BINDINGS
    public static GamepadKeys.Button DriveBaseModeSwitchButton = GamepadKeys.Button.Y,
            AirplaneLauncherLaunchButton = GamepadKeys.Button.B,
            ArmUpPositionBinding = GamepadKeys.Button.A,
            ArmDownPositionBinding = GamepadKeys.Button.X,
            ClawOpenCloseBinding = GamepadKeys.Button.LEFT_BUMPER,
            IMUPoseResetButton = GamepadKeys.Button.START;
}
