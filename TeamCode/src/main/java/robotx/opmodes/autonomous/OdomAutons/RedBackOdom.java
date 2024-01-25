package robotx.opmodes.autonomous.OdomAutons;

import com.acmerobotics.roadrunner.geometry.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.*;
import org.firstinspires.ftc.teamcode.util.*;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.*;

import robotx.modules.ArmSystem;
import robotx.modules.IntakeSystem;
import robotx.modules.LiftMotors;

//import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous(name = "RedBack Odom Drop,Place,Park", group = "Default")

public class RedBackOdom extends LinearOpMode {
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

        int sleepTime = 1000;
        /*
        TrajectorySequence fullAuton = drive.trajectorySequenceBuilder(new Pose2d(-36.00, -63.00, Math.toRadians(270.00)))
                .lineToLinearHeading(new Pose2d(-36.2, -33.15, Math.toRadians(0.00)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .splineTo(new Vector2d(-36.2, -10.46), Math.toRadians(0.00))
                .splineTo(new Vector2d(35.07, -12.98), Math.toRadians(0.00))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(51.39, -37.30), Math.toRadians(0.00))
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(50.50, -11.64))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(61.62, -11.35), Math.toRadians(0.00))
                .build();

         */
        TrajectorySequence fullAuton = drive.trajectorySequenceBuilder(new Pose2d(-35.00, -63.00, Math.toRadians(270.00)))
                .lineToLinearHeading(new Pose2d(-36, -33.15, Math.toRadians(0.00)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .lineToLinearHeading(new Pose2d(-36.00, -11.49, Math.toRadians(0.00)))
                .lineToConstantHeading(new Vector2d(34.48, -11.79))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .waitSeconds(2)

                .lineToConstantHeading(new Vector2d(54, -35))

                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(54, -12))

                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(62.06, -12))
                .build();
        drive.setPoseEstimate(fullAuton.start());

        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectorySequence(fullAuton);



        while (!isStopRequested() && opModeIsActive()){

        }
    }
}