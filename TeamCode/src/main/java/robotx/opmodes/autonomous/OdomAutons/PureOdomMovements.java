package robotx.opmodes.autonomous.OdomAutons;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.TrajectorySequence;

import robotx.modules.opmode.ArmSystem;
import robotx.modules.opmode.IntakeSystem;
import robotx.modules.opmode.LiftMotors;

//import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Disabled

@Autonomous(name = "PureOdomMiddleMovements", group = "")

public class PureOdomMovements extends LinearOpMode {

    ArmSystem armSystem;
    LiftMotors liftMotors;
    IntakeSystem intakeSystem;

    @Override
    public void runOpMode() {

        //Text at bottom of phone
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        armSystem = new ArmSystem(this);
        armSystem.init();

        intakeSystem = new IntakeSystem(this);
        intakeSystem.init();

        liftMotors = new LiftMotors(this);
        liftMotors.init();

        armSystem.start();
        intakeSystem.start();
        liftMotors.start();

        telemetry.addData(">", "Press Play to Start Op Mode");
        telemetry.update();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        telemetry.addData("Info", "\n a:RSR \n b:RSL \n x:BSR \n y:BSL \n ");
        telemetry.update();

        boolean programSelected = false;

        String sideSelect = null;

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

        telemetry.addData("Program running: ", sideSelect);
        telemetry.update();

        TrajectorySequence RSR = drive.trajectorySequenceBuilder(new Pose2d(11.5, -63.10, Math.toRadians(270)))
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

        TrajectorySequence BSL = drive.trajectorySequenceBuilder(new Pose2d(11.5, 63.10, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(12, 37, Math.toRadians(0))) //pixel on ground
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .splineTo(new Vector2d(38.93, 34.18), Math.toRadians(0.00))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(45.5, 23, Math.toRadians(0.00))) //drop on board
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(38, 61.17))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(58.50, 59.39), Math.toRadians(0.00))
                .build();

        telemetry.addData("Program running: ", sideSelect);
        telemetry.update();



        // add in BSR / RSL

        if (sideSelect.equals("RSR")){
            fullAuton = RSR;
        }
        if (sideSelect.equals("BSR")){
            //fullAuton = BSR;
        }
        if (sideSelect.equals("RSL")){
            //fullAuton = RSL;
        }
        if (sideSelect.equals("BSL")){
            fullAuton = BSL;
        }

        drive.setPoseEstimate(fullAuton.start());


        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectorySequence(fullAuton);



        while (!isStopRequested() && opModeIsActive()){

        }
    }
}