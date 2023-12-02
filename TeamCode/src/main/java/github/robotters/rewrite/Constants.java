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

    // GAME-PAD BINDINGS
    public static GamepadKeys.Button DriveBaseModeSwitchButton = GamepadKeys.Button.Y;

    public static GamepadKeys.Button AirplaneLauncherLaunchButton = GamepadKeys.Button.B;
}
