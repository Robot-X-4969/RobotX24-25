package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;


public class AutonMethods extends XModule {
    public Servo leftArm;
    public Servo rightArm;
    public Servo leftWrist;
    public Servo rightWrist;
    public Servo blockServo;
    public Servo launchArm;
    public Servo Stabilizer;
    public Servo leftIntake;
    public Servo rightIntake;

    public DcMotor IntakeMotor;
    public DcMotor GrappleMotor;
    public DcMotor LeftLift;
    public DcMotor RightLift;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    long t;

    //methods are built into one button as a toggle



    public void release()
    {
        blockServo.setPosition(.1);
    }



    public AutonMethods(OpMode op) {
        super(op);
    }
    public void init() {
        // pulls servos from configs
        leftArm = opMode.hardwareMap.servo.get("leftArm");
        rightArm = opMode.hardwareMap.servo.get("rightArm");
        leftWrist = opMode.hardwareMap.servo.get("leftWrist");
        rightWrist = opMode.hardwareMap.servo.get("rightWrist");
        blockServo = opMode.hardwareMap.servo.get("blockServo");
        launchArm = opMode.hardwareMap.servo.get("launchArm");
        GrappleMotor = opMode.hardwareMap.dcMotor.get("GrappleMotor");
        Stabilizer = opMode.hardwareMap.servo.get("Stabilizer");
        IntakeMotor = opMode.hardwareMap.dcMotor.get("IntakeMotor");
        leftIntake = opMode.hardwareMap.servo.get("leftIntake");
        rightIntake = opMode.hardwareMap.servo.get("rightIntake");
        LeftLift = opMode.hardwareMap.dcMotor.get("leftLift");
        RightLift = opMode.hardwareMap.dcMotor.get("rightLift");

        leftArm.setDirection(Servo.Direction.REVERSE);
        rightArm.setDirection(Servo.Direction.REVERSE);

        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE); //WHEN DOM TRAIN
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        //frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        //backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);  //WHEN DOM TRAIN

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
