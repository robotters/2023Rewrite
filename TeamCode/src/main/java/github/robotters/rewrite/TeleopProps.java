package github.robotters.rewrite;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;

// Props Specific to Teleops
public class TeleopProps {
    public final GamepadEx gamepad1;
    public final GamepadEx gamepad2;

    public TeleopProps(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = new GamepadEx(gamepad1);
        this.gamepad2 = new GamepadEx(gamepad2);
    }

    public enum Position {
        TOP,
        BOTTOM,
    }
}
