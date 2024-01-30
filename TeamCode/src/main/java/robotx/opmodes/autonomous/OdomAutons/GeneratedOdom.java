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

@Autonomous(name = "GeneratedOdom", group = "Default")

public class GeneratedOdom extends LinearOpMode {
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

        TrajectorySequence toDrop = drive.trajectorySequenceBuilder(new Pose2d(11.5, -63.10, Math.toRadians(270)))
                .lineToLinearHeading(new Pose2d(11.5, -37, Math.toRadians(270.00)))
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(-.25);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    intakeSystem.IntakeMotor.setPower(0);
                })
                .splineTo(new Vector2d(34.33, -35.67), Math.toRadians(0.00))

                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(49.31, -36.11, Math.toRadians(0.00)))

                .addTemporalMarker(() -> {
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(25.31, -36.11, Math.toRadians(0.00)))
                .addTemporalMarker(() -> {
                    armSystem.autonMoveArm();
                    armSystem.autonToggleBlock();
                })
                .waitSeconds(2)
                .splineTo(new Vector2d(58.80, -61.03), Math.toRadians(0.00))

                .build();


        drive.setPoseEstimate(toDrop.start());


        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectorySequence(toDrop);



        while (!isStopRequested() && opModeIsActive()){

        }
    }
}