package github.robotters.rewrite.autoutil;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

/*
 * The abstract class AutoSequence provides a series of sequential command groups that should
 * be run during the Auto Phase.
 * */
public abstract class AutoSequence {
    // The Init method Runs Before Start is Pressed on the Driver Hub
    public abstract SequentialCommandGroup init();

    // The Main Method Should be Scheduled immediately upon the start button being pressed
    public abstract SequentialCommandGroup main();
}
