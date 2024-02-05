package robotx.opmodes.autonomous.ParkEverywhere;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import robotx.modules.autonomous.MecanumDrive;
import robotx.modules.opmode.OrientationDrive;

@Autonomous(name = "ParkEverywhere", group = "ParkEverywhere")

public class ParkEverywhere extends LinearOpMode {

    //private ElapsedTime runtime = new ElapsedTime();

    //Modules being imported
    MecanumDrive mecanumDrive;
    OrientationDrive orientationDrive;
    //OdomSystem odomSystem;

    @Override

    public void runOpMode() {

        //Text at bottom of phone
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        mecanumDrive = new MecanumDrive(this);
        mecanumDrive.init();

        orientationDrive = new OrientationDrive(this);
        orientationDrive.init();

        mecanumDrive.start();
        orientationDrive.start();

        mecanumDrive.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mecanumDrive.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mecanumDrive.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mecanumDrive.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        telemetry.addData(">", "Press Play to Start Op Mode");
        telemetry.update();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Boolean programSelected = false;
        String sideSelect = "";

        telemetry.addData("Info", "\n a:RSR \n b:RSL \n x:BSR \n y:BSL \n ");
        telemetry.update();

        while (!programSelected){
            if (gamepad1.a){
                sideSelect = "RSR";
                programSelected = true;
            }
            if (gamepad1.b){
                sideSelect = "RSL";
                programSelected = true;
            }
            if (gamepad1.x){
                sideSelect = "BSR";
                programSelected = true;
            }
            if (gamepad1.y){
                sideSelect = "BSL";
                programSelected = true;
            }
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
                    sleep(50);
                    DriveForward(0.5, 1800);
                    sleep(1000);
                    break;
                case "RSL":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    StrafeLeft(.5,425);
                    sleep(1000);
                    DriveForward(0.5, 3000);
                    sleep(1000);
                    break;
                case "BSR":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    StrafeRight(.5,425);
                    sleep(1000);
                    DriveForward(0.5, 3000);
                    sleep(1000);
                    break;
                case "BSL":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    sleep(100);
                    DriveForward(0.5, 1800);
                    sleep(1000);
                    break;
            }
            //sleep until the end
            sleep(30000);

        }
    }


    public void DriveForward(double power, int time) {
        mecanumDrive.frontLeft.setPower(-power);  //top left when rev is down and ducky wheel is right
        mecanumDrive.frontRight.setPower(power); //bottom left
        mecanumDrive.backLeft.setPower(-power);   //top right
        mecanumDrive.backRight.setPower(power); // bottom right
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);  //top left when rev is down and ducky wheel is right
        mecanumDrive.frontRight.setPower(0); //bottom left
        mecanumDrive.backLeft.setPower(0);   //top right
        mecanumDrive.backRight.setPower(0); // bottom right
    }
    public void DriveStop() {
        mecanumDrive.frontLeft.setPower(0);  //top left when rev is down and ducky wheel is right
        mecanumDrive.frontRight.setPower(0); //bottom left
        mecanumDrive.backLeft.setPower(0);   //top right
        mecanumDrive.backRight.setPower(0); // bottom right
    }
    public void DriveBackward(double power, int time) {
        mecanumDrive.frontLeft.setPower(power);
        mecanumDrive.frontRight.setPower(-power);
        mecanumDrive.backLeft.setPower(power);
        mecanumDrive.backRight.setPower(power);
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);  //top left when rev is down and ducky wheel is right
        mecanumDrive.frontRight.setPower(0); //bottom left
        mecanumDrive.backLeft.setPower(0);   //top right
        mecanumDrive.backRight.setPower(0); // bottom right
    }
    public void StrafeRight(double power, int time) {
        mecanumDrive.frontLeft.setPower(-power);
        mecanumDrive.frontRight.setPower(-power);
        mecanumDrive.backLeft.setPower(power);
        mecanumDrive.backRight.setPower(power);
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);  //top left when rev is down and ducky wheel is right
        mecanumDrive.frontRight.setPower(0); //bottom left
        mecanumDrive.backLeft.setPower(0);   //top right
        mecanumDrive.backRight.setPower(0); // bottom right
    }
    public void StrafeLeft(double power, int time) {
        mecanumDrive.frontLeft.setPower(power);
        mecanumDrive.frontRight.setPower(power);
        mecanumDrive.backLeft.setPower(-power);
        mecanumDrive.backRight.setPower(-power);
        sleep(time);
        mecanumDrive.frontLeft.setPower(0);  //top left when rev is down and ducky wheel is right
        mecanumDrive.frontRight.setPower(0); //bottom left
        mecanumDrive.backLeft.setPower(0);   //top right
        mecanumDrive.backRight.setPower(0); // bottom right+
    }


}
