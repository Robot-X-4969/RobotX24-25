package robotx.opmodes.autonomous.CvOdom;

import android.util.Size;

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
import robotx.modules.MecanumDrive;
import robotx.modules.OpenCV;
import robotx.modules.OrientationDrive;
import robotx.modules.OpenCV;

@Autonomous(name = "CvOdomInit", group = "CvOdom")
public class CVOdomInit extends LinearOpMode {

    OpenCvWebcam phoneCam;
    MecanumDrive mecanumDrive;
    OrientationDrive orientationDrive;
    ArmSystem armSystem;
    IntakeSystem intakeSystem;
    LiftMotors liftMotors;

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * {@link #visionPortal} is the variable to store our instance of the vision portal.
     */

    @Override
    public void runOpMode() {


        // Setup, initialize, import modules

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        mecanumDrive = new MecanumDrive(this);
        mecanumDrive.init();

        orientationDrive = new OrientationDrive(this);
        orientationDrive.init();

        armSystem = new ArmSystem(this);
        armSystem.init();

        intakeSystem = new IntakeSystem(this);
        intakeSystem.init();

        liftMotors = new LiftMotors(this);
        liftMotors.init();

        mecanumDrive.start();
        orientationDrive.start();
        armSystem.start();
        intakeSystem.start();
        liftMotors.start();

        mecanumDrive.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mecanumDrive.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mecanumDrive.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mecanumDrive.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //testing var
        int sleepTime = 1000;

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
            public void onError(int errorCode) {} });
        sleep(sleepTime);
        String sideSelect = "RSR";
        telemetry.clearAll();
        telemetry.addData("Program running: ", sideSelect);
        telemetry.update();

        waitForStart();

        String pos = detector.getPosition();
        //neeeds to be dependent on pos on field

        telemetry.addData("Position: ", pos);
        telemetry.update();

        // close OpenCV camera
        phoneCam.stopStreaming();
        phoneCam.stopRecordingPipeline();
        phoneCam.closeCameraDevice();



        //code that runs
        while (opModeIsActive()) {

            //add in movements to here



            //sleep entire auto
            sleep(30000);

        }

    }
    //Controls
    public void DriveForward(double power, int time) {
        mecanumDrive.frontLeft.setPower(-power);  //top left when rev is down and ducky wheel is right
        mecanumDrive.frontRight.setPower(power); //bottom left
        mecanumDrive.backLeft.setPower(-power);   //top right
        mecanumDrive.backRight.setPower(power); // bottom right
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);
        mecanumDrive.frontRight.setPower(0);
        mecanumDrive.backLeft.setPower(0);
        mecanumDrive.backRight.setPower(0);
    }

    public void DriveBackward(double power, int time) {
        mecanumDrive.frontLeft.setPower(power);
        mecanumDrive.frontRight.setPower(-power);
        mecanumDrive.backLeft.setPower(power);
        mecanumDrive.backRight.setPower(-power);
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);
        mecanumDrive.frontRight.setPower(0);
        mecanumDrive.backLeft.setPower(0);
        mecanumDrive.backRight.setPower(0);
    }

    public void StrafeRight(double power, int time) {
        mecanumDrive.frontLeft.setPower(-power);
        mecanumDrive.frontRight.setPower(-power);
        mecanumDrive.backLeft.setPower(power);
        mecanumDrive.backRight.setPower(power);
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);
        mecanumDrive.frontRight.setPower(0);
        mecanumDrive.backLeft.setPower(0);
        mecanumDrive.backRight.setPower(0);
    }

    public void StrafeLeft(double power, int time) {
        mecanumDrive.frontLeft.setPower(power);
        mecanumDrive.frontRight.setPower(power);
        mecanumDrive.backLeft.setPower(-power);
        mecanumDrive.backRight.setPower(-power);
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);
        mecanumDrive.frontRight.setPower(0);
        mecanumDrive.backLeft.setPower(0);
        mecanumDrive.backRight.setPower(0);
    }

    public void DiagonalLeft(double power, int time){
        mecanumDrive.frontLeft.setPower(power);
        mecanumDrive.frontRight.setPower(-power);
        mecanumDrive.backLeft.setPower(power);
        mecanumDrive.backRight.setPower(-power);
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);
        mecanumDrive.frontRight.setPower(0);
        mecanumDrive.backLeft.setPower(0);
        mecanumDrive.backRight.setPower(0);
    }

    public void DiagonalRight(double power, int time){
        mecanumDrive.frontLeft.setPower(-power);
        mecanumDrive.frontRight.setPower(power);
        mecanumDrive.backLeft.setPower(-power);
        mecanumDrive.backRight.setPower(power);
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);
        mecanumDrive.frontRight.setPower(0);
        mecanumDrive.backLeft.setPower(0);
        mecanumDrive.backRight.setPower(0);
    }

    public void TurnLeft(double power, int time) {
        mecanumDrive.frontLeft.setPower(power);
        mecanumDrive.frontRight.setPower(-power);
        mecanumDrive.backLeft.setPower(-power);
        mecanumDrive.backRight.setPower(power);
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);
        mecanumDrive.frontRight.setPower(0);
        mecanumDrive.backLeft.setPower(0);
        mecanumDrive.backRight.setPower(0);
    }

    public void TurnRight(double power, int time) {
        mecanumDrive.frontLeft.setPower(-power);
        mecanumDrive.frontRight.setPower(power);
        mecanumDrive.backLeft.setPower(power);
        mecanumDrive.backRight.setPower(-power);
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);
        mecanumDrive.frontRight.setPower(0);
        mecanumDrive.backLeft.setPower(0);
        mecanumDrive.backRight.setPower(0);
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
