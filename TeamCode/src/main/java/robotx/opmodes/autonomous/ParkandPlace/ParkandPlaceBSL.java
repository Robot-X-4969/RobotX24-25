package robotx.opmodes.autonomous.ParkandPlace;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import robotx.modules.ArmSystem;
import robotx.modules.IntakeSystem;
import robotx.modules.LiftMotors;
import robotx.modules.MecanumDrive;
import robotx.modules.OrientationDrive;

@Autonomous(name = "ParkandPlaceBSL", group = "ParkandPlace")

public class ParkandPlaceBSL extends LinearOpMode {

    //private ElapsedTime runtime = new ElapsedTime();

    //Modules being imported
    MecanumDrive mecanumDrive;
    OrientationDrive orientationDrive;
    //OdomSystem odomSystem;
    ArmSystem armSystem;
    LiftMotors liftMotors;
    IntakeSystem intakeSystem;

    @Override

    public void runOpMode() {

        //Text at bottom of phone
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        mecanumDrive = new MecanumDrive(this);
        mecanumDrive.init();

        orientationDrive = new OrientationDrive(this);
        orientationDrive.init();

        //odomSystem = new OdomSystem(this);
        //odomSystem.init();

        armSystem = new ArmSystem(this);
        armSystem.init();

        intakeSystem = new IntakeSystem(this);
        intakeSystem.init();

        liftMotors = new LiftMotors(this);
        liftMotors.init();

        //odomSystem.start();
        mecanumDrive.start();
        orientationDrive.start();
        armSystem.start();
        intakeSystem.start();
        liftMotors.start();

        mecanumDrive.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mecanumDrive.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mecanumDrive.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mecanumDrive.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        telemetry.addData(">", "Press Play to Start Op Mode");
        telemetry.update();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        /*Pose2d start = new Pose2d(0,0,0);
        Vector2d board = new Vector2d(0,0);

        drive.setPoseEstimate(start);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(start)
                .lineToConstantHeading(board)
                .build();
        */

        double power = 0.5;
        int sleepTime = 1000;


        Boolean programSelected = false;
        String sideSelect = "";

        telemetry.update();

        while (!programSelected){

                sideSelect = "BSL";
                programSelected = true;

        }

        telemetry.addData("Program running: ", sideSelect);
        telemetry.update();

        waitForStart();


        //runtime.reset();

        if (opModeIsActive()) {
            //Function Sequence goes here

            // need to adjust times / powers

            // cstaut cooked here
            // 13V goal, 13.5V max, 12.65 min

            switch (sideSelect){
                case "RSR":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    sleep(100);
                    StrafeRight(-0.5,1200);
                    sleep(50);
                    ArmUp();
                    DriveForward(0.5,2000);
                    sleep(50);
                    Release(500);
                    sleep(510);
                    DriveBackward(0.5, 200);
                    sleep(10);
                    break;
                case "RSL":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    sleep(100);
                    StrafeRight(-0.25,3000);
                    sleep(50);
                    UnderBar(0.5,2250);
                    sleep(10);
                    Release(500);
                    sleep(510);
                    DriveBackward(0.5, 200);
                    sleep(10);
                    break;
                case "BSR":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    sleep(100);
                    StrafeRight(0.25,3000);
                    sleep(50);
                    UnderBar(0.5,250);
                    sleep(10);
                    Release(500);
                    sleep(510);
                    DriveBackward(0.5, 200);
                    sleep(10);
                    sleep(1000);
                    break;
                case "BSL":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    sleep(100);
                    StrafeRight(0.5,1200);
                    sleep(50);
                    ArmUp();
                    DriveForward(0.5,2000);
                    sleep(50);
                    Release(500);
                    sleep(510);
                    DriveBackward(0.5, 200);
                    sleep(10);
                    break;
            }
            //sleep until the end
            sleep(30000);

        }
    }


    public void DriveForward(double power, int time) {
        mecanumDrive.frontLeft.setPower(-power);  //top left when rev is down and ducky wheel is right
        mecanumDrive.frontRight.setPower(-power); //bottom left
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
        mecanumDrive.frontRight.setPower(power);
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
        mecanumDrive.frontRight.setPower(power);
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
        mecanumDrive.frontRight.setPower(-power);
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
    }

    public void UnderBar (double power, int time) {
        if(time < 1500){
            time = 1500;
        }
        mecanumDrive.frontLeft.setPower(-power);
        mecanumDrive.frontRight.setPower(-power);
        mecanumDrive.backLeft.setPower(-power);
        mecanumDrive.backRight.setPower(power);
        sleep(1500);
        ArmUp();
        sleep(time-15-00);
        mecanumDrive.frontLeft.setPower(0);
        mecanumDrive.frontRight.setPower(0);
        mecanumDrive.backLeft.setPower(0);
        mecanumDrive.backRight.setPower(0);
    }
}
