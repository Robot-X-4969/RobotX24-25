package robotx.opmodes.autonomous.CvOdom;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import robotx.modules.opmode.ArmSystem;
import robotx.modules.opmode.IntakeSystem;
import robotx.modules.opmode.LiftMotors;
import robotx.modules.autonomous.OpenCV;

import org.firstinspires.ftc.teamcode.drive.*;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.*;

@Autonomous(name = "CvOdomRSL", group = "CvOdom")
public class CvOdomRSL extends LinearOpMode {

    OpenCvWebcam phoneCam;
    ArmSystem armSystem;
    IntakeSystem intakeSystem;
    LiftMotors liftMotors;


    @Override
    public void runOpMode() {

        armSystem = new ArmSystem(this);
        armSystem.init();

        intakeSystem = new IntakeSystem(this);
        intakeSystem.init();

        liftMotors = new LiftMotors(this);
        liftMotors.init();

        armSystem.start();
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
        TrajectorySequence center = drive.trajectorySequenceBuilder(new Pose2d(-36, -63.1, Math.toRadians(-90)))
                .lineToLinearHeading(new Pose2d(-35.5, -33, Math.toRadians(-90)))
                .lineToLinearHeading(new Pose2d(-35.5, -37.5, Math.toRadians(-90)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .lineToLinearHeading(new Pose2d(-52,-39, Math.toRadians(0)))
                .lineToConstantHeading(new Vector2d(-52,-13))
                .lineToConstantHeading(new Vector2d(-31.96, -13))
                .splineToConstantHeading(new Vector2d(23.51, -10), Math.toRadians(0))
                .lineToConstantHeading(new Vector2d(23.51, -47))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(48.5, -47, Math.toRadians(0)))
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .lineToLinearHeading(new Pose2d(41.89, -16, Math.toRadians(10)))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(53, -18, Math.toRadians(95.00)))
                .build();

        //sets new pose2d for each pixel drop location

        // left
        TrajectorySequence fullAuton = drive.trajectorySequenceBuilder(new Pose2d(-36.00, -63.1, Math.toRadians(-90.00)))
                .lineToSplineHeading(new Pose2d(-39.00, -32, Math.toRadians(0.00)))
                .lineToConstantHeading(new Vector2d(-35.00, -32))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .lineToLinearHeading(new Pose2d(-38.00, -11.5,Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(23.51, -12, Math.toRadians(0)))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .splineToConstantHeading(new Vector2d(48.5, -32), Math.toRadians(0))
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .lineToConstantHeading(new Vector2d(39, -12))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(58, -13, Math.toRadians(90.00)))
                .build();

        // generated
        TrajectorySequence right = drive.trajectorySequenceBuilder(new Pose2d(-36, -63.10, Math.toRadians(-90)))
                .lineToLinearHeading(new Pose2d(-44.42, -35, Math.toRadians(180.00)))
                .lineToConstantHeading(new Vector2d(-30, -35))
                .lineToConstantHeading(new Vector2d(-36 , -35))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .splineTo(new Vector2d(-25.14, -12), Math.toRadians(0.00))
                .splineTo(new Vector2d(23.51, -12), Math.toRadians(0))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(47.5, -49,  Math.toRadians(-10.00)))
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .lineToConstantHeading(new Vector2d(38, -16))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(57, -18, Math.toRadians(90.00)))
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

        if (position.equals("Center")) {
            fullAuton = center;
        } else if (position.equals("Right")) {
            fullAuton = right;
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