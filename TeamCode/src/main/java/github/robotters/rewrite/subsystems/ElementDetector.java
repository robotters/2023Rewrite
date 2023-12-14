package github.robotters.rewrite.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Range;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import github.robotters.rewrite.RobotProps;


public class ElementDetector extends SubsystemBase {
    private OpenCvCamera cam;
    private ElementDetectorPipeline pipeline;

    public enum ElementPosition {
        LEFT,
        RIGHT,
        MIDDLE
    }

    public ElementDetector(HardwareMap hwMap, RobotProps.Color color) {
        WebcamName name = hwMap.get(WebcamName.class, "cam");
        cam = OpenCvCameraFactory.getInstance().createWebcam(name);
        pipeline = new ElementDetectorPipeline(color);
        cam.setPipeline(pipeline);
        cam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                cam.startStreaming(1280,720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });
    }

    public ElementPosition getPos() {
        cam.setPipeline(new DummyPipeline());
        return pipeline.pos;
    }
}

class ElementDetectorPipeline extends OpenCvPipeline {
    public ElementDetector.ElementPosition pos;
    private RobotProps.Color color;
    private Mat hsv= new Mat();
    private Mat bgr = new Mat();
    private Mat mask = new Mat();
    private Mat mask1 = new Mat();
    private Mat mask2 = new Mat();
    private MatOfDouble redLowerBound1 = new MatOfDouble(0.0, 120.0, 70.0);
    private MatOfDouble redUpperBound1 = new MatOfDouble(10.0, 255.0, 255.0);
    private MatOfDouble redLowerBound2 = new MatOfDouble(170.0, 120.0, 70.0);
    private MatOfDouble redUpperBound2 = new MatOfDouble(180.0, 255.0, 255.0);
    private MatOfDouble blueLowerBound = new MatOfDouble(105.0, 30.0, 30.0);
    private MatOfDouble blueUpperBound = new MatOfDouble(135.0, 255.0, 255.0);
    public ElementDetectorPipeline(RobotProps.Color color) {
        this.color = color;
    }
    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, bgr, Imgproc.COLOR_RGBA2BGR);
        Imgproc.cvtColor(bgr, hsv, Imgproc.COLOR_BGR2HSV);
        if (color == RobotProps.Color.RED) {
            Core.inRange(hsv, new Scalar(redLowerBound1.toArray()), new Scalar(redUpperBound1.toArray()), mask1);
            Core.inRange(hsv, new Scalar(redLowerBound2.toArray()), new Scalar(redUpperBound2.toArray()), mask2);
            Core.add(mask1, mask2, mask);
        } else {
            Core.inRange(hsv, new Scalar(blueLowerBound.toArray()), new Scalar(blueUpperBound.toArray()), mask);
        }
        int width = input.cols();
        int height = mask.rows();
        Mat leftThird = mask.submat(new Range(0, height), new Range(0, width / 3));
        Mat middleThird = mask.submat(new Range(0, height), new Range(width / 3, 2 * width / 3));
        Mat rightThird = mask.submat(new Range(0, height), new Range(2 * width / 3, width));

        double leftSum = Core.sumElems(leftThird).val[0];
        double middleSum = Core.sumElems(middleThird).val[0];
        double rightSum = Core.sumElems(rightThird).val[0];
        if (leftSum > middleSum && leftSum > rightSum) {
            pos = ElementDetector.ElementPosition.LEFT;
        } else if (middleSum > leftSum && middleSum > rightSum) {
            pos = ElementDetector.ElementPosition.MIDDLE;
        } else {
            pos = ElementDetector.ElementPosition.RIGHT;
        }
        return mask;
    }
}

class DummyPipeline extends OpenCvPipeline {
    @Override
    public Mat processFrame(Mat input) {
        return input;
    }
}
