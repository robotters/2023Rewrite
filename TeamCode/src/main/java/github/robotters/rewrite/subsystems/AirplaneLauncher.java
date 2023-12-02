package github.robotters.rewrite.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import github.robotters.rewrite.Constants;

public class AirplaneLauncher extends SubsystemBase {
    public final Servo mServo;
    public PlaneLauncherReadiness planeLauncherReadiness = PlaneLauncherReadiness.READY;

    public AirplaneLauncher(HardwareMap hwMap) {
        mServo = hwMap.get(Servo.class, Constants.AirplaneLauncherServoKey);
        mServo.setPosition(Constants.AirplaneLauncherStartPos);
    }

    public void Launch() {
        if (!(planeLauncherReadiness == PlaneLauncherReadiness.READY)) {
            return;
        }

        mServo.setPosition(Constants.AirplaneLauncherEndPos);
        planeLauncherReadiness = PlaneLauncherReadiness.LAUNCHED;
    }

    enum PlaneLauncherReadiness {
        READY,
        LAUNCHED
    }
}
