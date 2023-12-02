package github.robotters.rewrite.util;


import github.robotters.rewrite.Robot;
import github.robotters.rewrite.RobotProps;

public class RobotHolder {
    private static Robot robot;

    private RobotHolder() {}

    // Hide Robot Behind State Container, allowing subsystem state to remain while scheduler resets.
    public static Robot getRobot(RobotProps props) {
        if (robot == null) {
            robot = new Robot(props);
        }

        return robot;
    }
}
