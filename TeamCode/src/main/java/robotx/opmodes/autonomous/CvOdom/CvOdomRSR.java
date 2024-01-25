package robotx.opmodes.autonomous.CvOdom;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.*;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.*;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import robotx.modules.ArmSystem;
import robotx.modules.IntakeSystem;
import robotx.modules.LiftMotors;
import robotx.modules.OpenCV;

@Autonomous(name = "CvOdomRSR", group = "CvOdom")
public class CvOdomRSR extends LinearOpMode {

    OpenCvWebcam phoneCam;
    ArmSystem armSystem;
    IntakeSystem intakeSystem;
    LiftMotors liftMotors;

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera


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
        int sleepTime = 1000;

        //openCV camera / pipeline setup
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        OpenCV detector = new OpenCV(telemetry);
        phoneCam.setPipeline(detector);

        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.
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

        // init pixelPos
        String pixelPos = "None";

        Boolean programSelected = false;
        String sideSelect = "";


        while (!programSelected){

            sideSelect = "RSR";
            programSelected = true;

        }

        telemetry.clearAll();

        telemetry.addData("Program running: ", sideSelect);
        telemetry.update();
        telemetry.addData("current run", sideSelect);
        telemetry.update();



        String position = detector.getPosition();



        Pose2d intakeDropPose = new Pose2d(11.5, -37, Math.toRadians(270));
        Pose2d dropPose = new Pose2d(49.31, -36.11, Math.toRadians(0.00));
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        // defaults to middle bc that works the most consistently
        TrajectorySequence fullAuton = drive.trajectorySequenceBuilder(new Pose2d(11.5, -63.10, Math.toRadians(270)))
                .lineToLinearHeading(intakeDropPose)
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
                .lineToLinearHeading(dropPose)
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
        if (position.equals("Left")) {
            intakeDropPose = new Pose2d(11.5,-37, Math.toRadians(0));
            fullAuton = drive.trajectorySequenceBuilder(new Pose2d(11.20, -61.92, Math.toRadians(270)))
                    .lineToLinearHeading(intakeDropPose)
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
                    .lineToLinearHeading(dropPose)
                    .addTemporalMarker(() -> {
                        armSystem.autonToggleBlock();
                    })
                    .waitSeconds(2)
                    .lineToConstantHeading(new Vector2d(45.45, -61.17))
                    .addTemporalMarker(() -> {
                        armSystem.autonMoveArm();
                        armSystem.autonToggleBlock();
                    })
                    .waitSeconds(2)
                    .splineTo(new Vector2d(58.50, -59.39), Math.toRadians(0.00))
                    .build();
        } else if (position.equals("Right")) {
            fullAuton = drive.trajectorySequenceBuilder(new Pose2d(11.20, -61.92, Math.toRadians(270)))
                    .lineToLinearHeading(new Pose2d(24.00, -40.00, Math.toRadians(270.00)))
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
                    .lineToConstantHeading(new Vector2d(51.53, -41.45))
                    .addTemporalMarker(() -> {
                        armSystem.autonToggleBlock();
                    })
                    .waitSeconds(2)
                    .lineToConstantHeading(new Vector2d(43.38, -61.32))
                    .addTemporalMarker(() -> {
                        armSystem.autonMoveArm();
                        armSystem.autonToggleBlock();
                    })
                    .waitSeconds(2)
                    .splineTo(new Vector2d(58.21, -59.69), Math.toRadians(0.00))
                    .build();
            }




            drive.setPoseEstimate(fullAuton.start());



            telemetry.addData("Status", "Initialized");
            telemetry.update();
            waitForStart();

            if (isStopRequested()) return;

            drive.followTrajectorySequence(fullAuton);



            while (!isStopRequested() && opModeIsActive()){

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
