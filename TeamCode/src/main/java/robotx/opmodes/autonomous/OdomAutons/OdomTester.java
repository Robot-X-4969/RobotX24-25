package robotx.opmodes.autonomous.OdomAutons;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.TrajectorySequence;

import robotx.modules.opmode.ArmSystem;
import robotx.modules.opmode.IntakeSystem;
import robotx.modules.opmode.LiftMotors;

//import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous(name = "OdomTester ", group = "Default")

public class OdomTester extends LinearOpMode {
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

        //odomSystem.start();
        armSystem.start();
        intakeSystem.start();
        liftMotors.start();


        telemetry.addData(">", "Press Play to Start Op Mode");
        telemetry.update();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        int sleepTime = 1000;


        Pose2d startingPosition = new Pose2d(14.5, -62.75, Math.toRadians(90.00));
        TrajectorySequence fullAuton = drive.trajectorySequenceBuilder(startingPosition.plus((new Pose2d(0, -0.1, 0))))
                .lineToLinearHeading((startingPosition.plus(new Pose2d(0, -0.1, 0))).plus(new Pose2d(0, -24, 0)))
                .build();
        Trajectory myTrajectory = drive.trajectoryBuilder(startingPosition.plus((new Pose2d(0, 0, -120))))
                .forward(5)

                .build();

        TrajectorySequence untitled0 = drive.trajectorySequenceBuilder(new Pose2d(-36.00, -60.00, Math.toRadians(90.00)))
                .lineToLinearHeading(new Pose2d(-36.56, -24.25, Math.toRadians(90.00)))
                .lineToLinearHeading(new Pose2d(-37.00, -32.00, Math.toRadians(92.94)))
                .lineToLinearHeading(new Pose2d(12.00, -33.00, Math.toRadians(88.60)))
                .lineToLinearHeading(new Pose2d(47.73, -33.20, Math.toRadians(-0.33)))
                .build();


        drive.setPoseEstimate(startingPosition);

        waitForStart();


        drive.followTrajectory(myTrajectory);

        if (isStopRequested()) return;

        while (!isStopRequested() && opModeIsActive()){
            drive.followTrajectorySequence(untitled0);
            //drive.followTrajectory(turnAndPlace);
        }
    }
}