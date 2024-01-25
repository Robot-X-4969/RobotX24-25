package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.modules.ArmSystem;
import robotx.modules.IntakeSystem;
import robotx.modules.LiftMotors;
import robotx.modules.MecanumDrive;
import robotx.modules.OdomSystem;
import robotx.modules.OrientationDrive;

 @Autonomous(name = "MainAutonRight", group = "Default")
 
 public class RobotXAutonomous2021 extends LinearOpMode {

     //private ElapsedTime runtime = new ElapsedTime();

     //Modules being imported
     MecanumDrive mecanumDrive;
     OrientationDrive orientationDrive;
     OdomSystem odomSystem;
     ArmSystem armSystem;
     IntakeSystem intakeSystem;
     LiftMotors liftMotors;

     @Override
     public void runOpMode() {

         //Text at bottom of phone
         telemetry.addData("Status", "Initialized");
         telemetry.update();

         mecanumDrive = new MecanumDrive(this);
         mecanumDrive.init();

         orientationDrive = new OrientationDrive(this);
         orientationDrive.init();

         odomSystem = new OdomSystem(this);
         odomSystem.init();

         armSystem = new ArmSystem(this);
         armSystem.init();

         intakeSystem = new IntakeSystem(this);
         intakeSystem.init();

         liftMotors = new LiftMotors(this);
         liftMotors.init();

         odomSystem.start();
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

         double power = 0.5;
         int sleepTime = 1000;

         waitForStart();

         //runtime.reset();

         if (opModeIsActive()) {
             //Function Sequence goes here

             StrafeRight(1,400);

         }
     }

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
         armSystem.leftWrist.setPosition(.15);
         armSystem.rightWrist.setPosition(.95);
         armSystem.leftArm.setPosition(.29);
         armSystem.rightArm.setPosition(.70);
     }

     public void ArmUp () {
         armSystem.leftWrist.setPosition((.565));
         armSystem.rightWrist.setPosition((.485));
         sleep(500);
         armSystem.leftWrist.setPosition(.98);
         armSystem.rightWrist.setPosition(.02);
         armSystem.leftArm.setPosition(.542);
         armSystem.rightArm.setPosition(0.53);
     }

     public void Release(int time) {
         armSystem.blockServo.setPosition(.6);
         sleep(time);
         armSystem.blockServo.setPosition(.1);
     }

 }
