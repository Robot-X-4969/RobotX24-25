package robotx.opmodes.autonomous.ParkandPlaceandMove;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import robotx.modules.autonomous.AutonMethods;

@Disabled

@Autonomous(name = "ParkandPlaceandMoveRSR", group = "ParkandPlaceandMove")

public class ParkandPlaceandMoveRSR extends LinearOpMode {

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
            telemetry.addData("current run", sideSelect);
            telemetry.update();
            switch (sideSelect){
                case "RSR":
                    sleep(100);
                    autonMethods.StrafeRight(-0.5);
                    sleep(1200);
                    autonMethods.DriveStop();
                    sleep(50);
                    //autonMethods.ArmUp();
                    autonMethods.DriveForward(0.5);
                    sleep(2000);
                    autonMethods.DriveStop();
                    sleep(50);
                    //autonMethods.Release();
                    sleep(510);
                    //autonMethods.Close();
                    autonMethods.DriveBackward(0.5);
                    sleep(200);
                    autonMethods.DriveStop();
                    sleep(10);
                    autonMethods.StrafeLeft(0.5);
                    sleep(1200);
                    autonMethods.DriveStop();
                    sleep(10);
                    //autonMethods.ArmRest();
                    sleep(500);
                    autonMethods.DriveForward(0.5);
                    sleep(500);
                    autonMethods.DriveStop();
                    sleep(1000);
                    break;
                case "RSL" :
                    //autonMethods.StrafeRight(-0.5,1100);
                    sleep(1100);
                    autonMethods.DriveStop();
                    sleep(50);
                    //UnderBar(0.5,2250);
                    sleep(10);
                    autonMethods.Release();
                    sleep(510);
                    autonMethods.Close();
                    autonMethods.DriveBackward(0.5);
                    sleep(200);
                    autonMethods.DriveStop();
                    sleep(10);
                    autonMethods.StrafeLeft(-0.5);
                    sleep(1200);
                    autonMethods.DriveStop();
                    sleep(10);
                    autonMethods.ArmRest();
                    sleep(500);
                    autonMethods.DriveForward(0.5);
                    sleep(500);
                    autonMethods.DriveStop();
                    sleep(1000);
                    break;
                case "BSL" :

                    break;
                case "BSR" :

                    break;
            }
            //sleep until the end
            sleep(30000);

        }
    }
}
