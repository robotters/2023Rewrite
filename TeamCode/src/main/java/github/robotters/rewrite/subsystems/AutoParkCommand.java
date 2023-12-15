package github.robotters.rewrite.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

public class AutoParkCommand extends CommandBase {
    private final DriveTrain mDriveTrain;
    private ElapsedTime runtime = new ElapsedTime();

    public AutoParkCommand(DriveTrain mDriveTrain) {
        this.mDriveTrain = mDriveTrain;
        addRequirements(mDriveTrain);
    }

    @Override
    public void initialize() {
        runtime.reset();
    }

    @Override
    public void execute() {
        mDriveTrain.DriveRobotOriented(0.0, 0.2, 0.0);
    }

    @Override
    public boolean isFinished() {
        return runtime.seconds() < 2;
    }
}
