package github.robotters.rewrite.util;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

// The Bulk Reader Class Provides the Ability to use IMU Bulk Reads For OpModes
public class BulkReader {
    private final List<LynxModule> mHubs;

    public BulkReader(HardwareMap hwMap) {
        mHubs = hwMap.getAll(LynxModule.class);

        for (LynxModule m : mHubs) {
            m.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }

    // Must Be Called Per Robot Periodic To Prevent Stale Reads
    public void bulkRead() {
        for (LynxModule m : mHubs) {
            m.clearBulkCache();
        }
    }
}
