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



        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence center = drive.trajectorySequenceBuilder(new Pose2d(11.5, -63.10, Math.toRadians(270)))
                .lineToLinearHeading(new Pose2d(20, -25.5, Math.toRadians(0)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2.5)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .splineTo(new Vector2d(34.33, -35.67), Math.toRadians(0.00))

                .addTemporalMarker(() -> {
                    armSystem.moveArm();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(48, -37, Math.toRadians(0.00)))
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    armSystem.moveArm();
                })
                .lineToLinearHeading(new Pose2d(25.31, -36.11, Math.toRadians(0.00)))
                .addTemporalMarker(() -> {
                    armSystem.moveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(50.80, -62, Math.toRadians(90.00)))
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
                    armSystem.moveArm();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(47.5, -31, Math.toRadians(0.00)))
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    armSystem.moveArm();
                })
                .lineToConstantHeading(new Vector2d(40, -61.17))
                .addTemporalMarker(() -> {
                    armSystem.moveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(50.50, -62, Math.toRadians(90.00)))
                .build();

        TrajectorySequence right = drive.trajectorySequenceBuilder(new Pose2d(11.5, -63.10, Math.toRadians(270)))
                .lineToLinearHeading(new Pose2d(22.00, -35.00, Math.toRadians(270.00))) //john added
                .lineToLinearHeading(new Pose2d(22.00, -42.00, Math.toRadians(270.00)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2.5)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .splineTo(new Vector2d(43.23, -42.19), Math.toRadians(0.00))
                .addTemporalMarker(() -> {
                    armSystem.moveArm();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(47.5, -44))
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    armSystem.moveArm();
                })
                .lineToConstantHeading(new Vector2d(40.38, -61.32))
                .addTemporalMarker(() -> {
                    armSystem.moveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(54.21, -59.69), Math.toRadians(90.00))
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