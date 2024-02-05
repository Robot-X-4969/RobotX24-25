package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import robotx.modules.autonomous.AutonMethods;

@Autonomous(name = "ParkandPlace", group = "ParkandPlace")

public class ParkandPlace extends LinearOpMode {

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
            telemetry.addData("current run", sideSelect);
            telemetry.update();
            switch (sideSelect) {
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
                    autonMethods.Release();
                    sleep(510);
                    autonMethods.Close();
                    sleep(1000);
                    break;
                case "RSL":
                    autonMethods.StrafeRight(-0.5);
                    sleep(1100);
                    autonMethods.DriveStop();
                    sleep(1100);
                    autonMethods.DriveStop();
                    sleep(50);
                    autonMethods.DriveForward(0.5);
                    sleep(1500);
                    autonMethods.ArmUp();
                    sleep(1400);
                    autonMethods.DriveStop();
                    sleep(10);
                    autonMethods.Release();
                    sleep(510);
                    autonMethods.Close();
                    sleep(1000);
                    break;
                case "BSR":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    sleep(100);
                    autonMethods.StrafeRight(0.25);
                    sleep(3000);
                    autonMethods.DriveStop();
                    sleep(50);
                    autonMethods.DriveForward(0.5);
                    sleep(1500);
                    autonMethods.ArmUp();
                    sleep(1400);
                    autonMethods.DriveStop();
                    sleep(10);
                    autonMethods.Release();
                    sleep(500);
                    autonMethods.Close();
                    sleep(1000);
                    break;
                case "BSL":
                    telemetry.addData("current run", sideSelect);
                    telemetry.update();
                    autonMethods.StrafeRight(0.5);
                    sleep(1200);
                    autonMethods.DriveStop();
                    sleep(50);
                    autonMethods.ArmUp();
                    autonMethods.DriveForward(0.5);
                    sleep(15-00);
                    autonMethods.DriveStop();
                    sleep(50);
                    autonMethods.Release();
                    sleep(500);
                    autonMethods.Close();
                    sleep(1000);
                    break;
            }
            //sleep until the end
            sleep(30000);
        }
    }
}