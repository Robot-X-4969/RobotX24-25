package robotx.opmodes.autonomous.CvOdom;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.List;

import robotx.modules.ArmSystem;
import robotx.modules.IntakeSystem;
import robotx.modules.LiftMotors;
import robotx.modules.OpenCV;
import robotx.modules.OrientationDrive;

import org.firstinspires.ftc.teamcode.drive.*;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.*;

@Autonomous(name = "CvOdomBSR", group = "CvOdom")
public class CvOdomBSR extends LinearOpMode {

    OpenCvWebcam phoneCam;
    //ArmSystem armSystem;
    IntakeSystem intakeSystem;
    LiftMotors liftMotors;


    @Override
    public void runOpMode() {

        //armSystem = new ArmSystem(this);
        //armSystem.init();

        intakeSystem = new IntakeSystem(this);
        intakeSystem.init();

        liftMotors = new LiftMotors(this);
        liftMotors.init();

        //armSystem.start();
        intakeSystem.start();
        liftMotors.start();


        //testing var
        int sleepTime = 2500;
        //openCV camera / pipeline setup
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        OpenCV detector = new OpenCV(telemetry);
        phoneCam.setPipeline(detector);
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);



        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        // generated
        TrajectorySequence center = drive.trajectorySequenceBuilder(new Pose2d(-36, 63.1, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-36, 38, Math.toRadians(90)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .lineToLinearHeading(new Pose2d(-52,39, Math.toRadians(0)))
                .lineToConstantHeading(new Vector2d(-52,13))
                .lineToConstantHeading(new Vector2d(-31.96, 13))
                .splineToConstantHeading(new Vector2d(23.51, 13), Math.toRadians(0))
                .addTemporalMarker(() -> {
                    //armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(45.5, 27.5, Math.toRadians(0)))
                .addTemporalMarker(() -> {
                    //armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(41.89, 9, Math.toRadians(-10)))
                .addTemporalMarker(() -> {
                    //armSystem.autonMoveArm();
                    //armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(50, 8), Math.toRadians(-10.00))
                .build();

        //sets new pose2d for each pixel drop location

        // right (generated)
        TrajectorySequence fullAuton = drive.trajectorySequenceBuilder(new Pose2d(-36.00, 63.1, Math.toRadians(90.00)))
                .lineToSplineHeading(new Pose2d(-39.00, 34.48, Math.toRadians(0.00)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .lineToConstantHeading(new Vector2d(-38.00, 10.46))
                .lineToConstantHeading(new Vector2d(23.51, 13.12))
                .addTemporalMarker(() -> {
                    //armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .splineToConstantHeading(new Vector2d(47.50, 33), Math.toRadians(0))
                .addTemporalMarker(() -> {
                    //armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(41.89, 10.46))
                .addTemporalMarker(() -> {
                    //armSystem.autonMoveArm();
                    //armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(62.51, 11.79), Math.toRadians(0.00))
                .build();

        // generated
        TrajectorySequence left = drive.trajectorySequenceBuilder(new Pose2d(-36, 63.10, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-44.42, 33.15, Math.toRadians(180.00)))
                .lineToConstantHeading(new Vector2d(-37.00, 33.00))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .splineTo(new Vector2d(-25.14, 12), Math.toRadians(0.00))
                .splineTo(new Vector2d(23.51, 13.12), Math.toRadians(0))
                .addTemporalMarker(() -> {
                    //armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(45, 43))
                .addTemporalMarker(() -> {
                    //armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(38, 14))
                .addTemporalMarker(() -> {
                    //armSystem.autonMoveArm();
                    //armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(57, 14), Math.toRadians(0.00))
                .build();


        waitForStart();

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                phoneCam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_RIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
        sleep(sleepTime);
        String position = detector.getPosition();
        sleep(sleepTime/4 );
        // JUST FOR TESTING TAKE OUT
        position = "Right";
        // ^^^^^^^^^^^^^^
        if (position.equals("Center")) {
            fullAuton = center;
        } else if (position.equals("Left")) {
            fullAuton = left;
        }
        phoneCam.stopStreaming();
        phoneCam.stopRecordingPipeline();
        phoneCam.closeCameraDevice();

        drive.setPoseEstimate(fullAuton.start());
        drive.followTrajectorySequence(fullAuton);

        // defaults to middle bc that works the most consistently


        while (!isStopRequested() && opModeIsActive()) {

        }

    }


    /* flipped drop side left
    TrajectorySequence fullAuton = drive.trajectorySequenceBuilder(new Pose2d(-36.11, 61.32, Math.toRadians(448.60)))
        .lineToSplineHeading(new Pose2d(-44.42, 33.15, Math.toRadians(180.00)))
        .lineToConstantHeading(new Vector2d(-34.00, 33.00))
        .splineTo(new Vector2d(-25.14, 10.90), Math.toRadians(360.00))
        .splineTo(new Vector2d(23.51, 13.12), Math.toRadians(359.38))
        .splineToConstantHeading(new Vector2d(49.90, 28.55), Math.toRadians(358.28))
        .lineToConstantHeading(new Vector2d(41.89, 10.46))
        .splineTo(new Vector2d(62.51, 11.79), Math.toRadians(360.00))
        .build();
    flipped drop side right
    fullAuton = drive.trajectorySequenceBuilder(new Pose2d(11.20, 61.92, Math.toRadians(91.33)))
        .lineToLinearHeading(new Pose2d(24.00, 40.00, Math.toRadians(90.00)))
        .splineTo(new Vector2d(43.23, 42.19), Math.toRadians(360.00))
        .lineToConstantHeading(new Vector2d(51.53, 41.45))
        .lineToConstantHeading(new Vector2d(46.05, 60.73))
        .splineTo(new Vector2d(58.21, 61.77), Math.toRadians(0.00))
        .build();

     */





    // special note for John - sleeps are to give the servos time to move

}