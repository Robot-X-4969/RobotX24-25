package robotx.opmodes.autonomous.OdomAutons;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.TrajectorySequence;

import robotx.modules.ArmSystem;
import robotx.modules.IntakeSystem;
import robotx.modules.LiftMotors;

//import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous(name = "BlueFront Odom Drop,Place,Park", group = "Default")

public class BlueFrontOdom extends LinearOpMode {
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


        TrajectorySequence fullAuton = drive.trajectorySequenceBuilder(new Pose2d(11.50, 63.00, Math.toRadians(90.00)))
                .lineToLinearHeading(new Pose2d(12.00, 31.96, Math.toRadians(360)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                    armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .splineTo(new Vector2d(50.79, 31.07), Math.toRadians(360.00))
                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToConstantHeading(new Vector2d(49.90, 60.73))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(58.80, 60.28), Math.toRadians(360.00))
                .build();
        drive.setPoseEstimate(fullAuton.start());

        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectorySequence(fullAuton);



        while (!isStopRequested() && opModeIsActive()){

        }
    }
}