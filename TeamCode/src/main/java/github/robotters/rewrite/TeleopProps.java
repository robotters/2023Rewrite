package github.robotters.rewrite;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;

// Props Specific to Teleops
public class TeleopProps {
    public final GamepadEx gamepad1;

    public TeleopProps(Gamepad gamepad1) {
        this.gamepad1 = new GamepadEx(gamepad1);
    }
}
