package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import robotx.modules.autonomous.AutonMethods;

@Autonomous(name = "ParkEverywhere", group = "ParkEverywhere")

public class ParkEverywhere extends LinearOpMode {

    //private ElapsedTime runtime = new ElapsedTime();

    //Modules being imported

    AutonMethods autonMethods;

    @Override

    public void runOpMode() {

        //Text at bottom of phone
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        autonMethods = new AutonMethods(this);
        autonMethods.init();

        autonMethods.start();


        telemetry.addData(">", "Press Play to Start Op Mode");
        telemetry.update();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Boolean programSelected = false;
        String sideSelect = "";

        telemetry.addData("Info", "\n a:RSR \n b:RSL \n x:BSR \n y:BSL \n ");
        telemetry.update();

        while (!programSelected) {
            if (gamepad1.a) {
                sideSelect = "RSR";
                programSelected = true;
            }
            if (gamepad1.b) {
                sideSelect = "RSL";
                programSelected = true;
            }
            if (gamepad1.x) {
                sideSelect = "BSR";
                programSelected = true;
            }
            if (gamepad1.y) {
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

            switch (sideSelect) {
                case "RSR":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    sleep(100);
                    autonMethods.DriveForward(0.5);
                    sleep(1800);
                    autonMethods.DriveStop();
                    sleep(1000);
                    break;
                case "RSL":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    autonMethods.StrafeLeft(.5);
                    sleep(425);
                    autonMethods.DriveStop();
                    sleep(1000);
                    autonMethods.DriveForward(0.5);
                    sleep(3000);
                    autonMethods.DriveStop();
                    sleep(1000);
                    break;
                case "BSR":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    autonMethods.StrafeRight(.5);
                    sleep(425);
                    autonMethods.DriveStop();
                    sleep(1000);
                    autonMethods.DriveForward(0.5);
                    sleep(3000);
                    autonMethods.DriveStop();
                    sleep(1000);
                    break;
                case "BSL":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    sleep(100);
                    autonMethods.DriveForward(0.5);
                    sleep(1800);
                    autonMethods.DriveStop();
                    sleep(1000);
                    break;
            }
            //sleep until the end
            sleep(30000);

        }
    }


}
