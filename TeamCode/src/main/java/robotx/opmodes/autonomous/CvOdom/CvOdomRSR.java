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

@Autonomous(name = "CvOdomRSR", group = "CvOdom")
public class CvOdomRSR extends LinearOpMode {

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



        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence center = drive.trajectorySequenceBuilder(new Pose2d(11.5, -63.10, Math.toRadians(270)))
                .lineToLinearHeading(new Pose2d(11.5, -37, Math.toRadians(270)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .splineTo(new Vector2d(34.33, -35.67), Math.toRadians(0.00))

                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(48, -36.11, Math.toRadians(0.00)))
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(25.31, -36.11, Math.toRadians(0.00)))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(58.80, -61.03), Math.toRadians(0.00))
                .build();

        //sets new pose2d for each pixel drop location

        // left
        TrajectorySequence fullAuton = drive.trajectorySequenceBuilder(new Pose2d(11.5, -63.10, Math.toRadians(270)))
                .lineToLinearHeading(new Pose2d(11.5, -37, Math.toRadians(0)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .splineTo(new Vector2d(38.93, -34.18), Math.toRadians(0.00))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(47.5, -33, Math.toRadians(0.00)))
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(40, -61.17))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(58.50, -61), Math.toRadians(0.00))
                .build();

        TrajectorySequence right = drive.trajectorySequenceBuilder(new Pose2d(11.5, -63.10, Math.toRadians(270)))
                .lineToLinearHeading(new Pose2d(21.00, -40.00, Math.toRadians(270.00)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .splineTo(new Vector2d(43.23, -42.19), Math.toRadians(0.00))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(47.5, -44))
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(40.38, -61.32))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(58.21, -59.69), Math.toRadians(0.00))
                .build();


        waitForStart();

        String position = detector.getPosition();
        sleep(sleepTime);
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
    fullAuton = drive.trajectorySequenceBuilder(new Pose2d(11.20, 61.92, Math.toRadians(90)))
        .lineToLinearHeading(new Pose2d(13.72, 34.48, Math.toRadians(360.00)))
        .splineTo(new Vector2d(38.93, 34.18), Math.toRadians(360.00))
        .splineTo(new Vector2d(51.68, 30.18), Math.toRadians(357.95))
        .lineToConstantHeading(new Vector2d(45.45, 61.17))
        .splineTo(new Vector2d(59.39, 61.92), Math.toRadians(0.00))
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





    public void Intake(double power, int time) {
        intakeSystem.IntakeMotor.setPower(power);
        sleep(time);
        intakeSystem.IntakeMotor.setPower(0);
    }

    public void Unintake(double power, int time) {
        intakeSystem.IntakeMotor.setPower(-power);
        sleep(time);
        intakeSystem.IntakeMotor.setPower(0);
    }

    public void FirstLift() {
        double liftPower = 1;
        int liftTime = 100;
        liftMotors.LeftLift.setPower(liftPower);
        liftMotors.RightLift.setPower(-liftPower);
        sleep(liftTime);
        liftMotors.LeftLift.setPower(0);
        liftMotors.RightLift.setPower(0);
    }

    public void RaiseLift(double power, int time) {
        liftMotors.LeftLift.setPower(power);
        liftMotors.RightLift.setPower(-power);
        sleep(time);
        liftMotors.LeftLift.setPower(0);
        liftMotors.RightLift.setPower(0);
    }

    public void LowerLift(double power, int time) {
        liftMotors.LeftLift.setPower(-power);
        liftMotors.RightLift.setPower(power);
        sleep(time);
        liftMotors.LeftLift.setPower(0);
        liftMotors.RightLift.setPower(0);
    }

    public void ArmRest () {
        armSystem.leftWrist.setPosition(.175);
        armSystem.rightWrist.setPosition(.925);
        armSystem.leftArm.setPosition(.274);
        armSystem.rightArm.setPosition(.712);
    }

    public void ArmUp () {
        armSystem.leftWrist.setPosition((.5775));
        armSystem.rightWrist.setPosition((.2525));
        sleep(500);
        armSystem.leftWrist.setPosition(.86);
        armSystem.rightWrist.setPosition(.14);
        armSystem.leftArm.setPosition(.522);
        armSystem.rightArm.setPosition(0.55);
    }

    public void Release(int time) {
        armSystem.blockServo.setPosition(.6);
        sleep(time);
        armSystem.blockServo.setPosition(.1);
        sleep(time);
    }

    public void ScoreAPixel(int time){
        ArmUp();
        Release(time);
        ArmRest();

    }

    // special note for John - sleeps are to give the servos time to move

}