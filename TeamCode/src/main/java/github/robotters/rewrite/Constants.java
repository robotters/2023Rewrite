package github.robotters.rewrite;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class Constants {
    // DriveBase Info
    public static String LeftFrontKey = "left-front",
            RightFrontKey = "right-front",
            LeftBackKey = "left back",
            RightBackKey = "right-back";

    // Airplane Launcher Info
    public static String AirplaneLauncherServoKey = "airplane-servo";
    public static double AirplaneLauncherStartPos = 0.0, AirplaneLauncherEndPos = 1.0;

    // ARM INFO:
    public static String ArmMotorKey = "arm-motor";

    public static double ArmKp = 1.0, ArmKi = 0.0, ArmKd = 0.0;

    // GAME-PAD BINDINGS
    public static GamepadKeys.Button DriveBaseModeSwitchButton = GamepadKeys.Button.Y,
            AirplaneLauncherLaunchButton = GamepadKeys.Button.B,
            ArmUpPositionBinding = GamepadKeys.Button.A,
            ArmDownPositionBinding = GamepadKeys.Button.X;
}
