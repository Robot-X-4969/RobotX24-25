package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class LiftSystem extends XModule {

    // motors being used

    public static boolean toggle = true;

    public Servo liftServo1;
    public Servo liftServo2;

    public double power = 1;

    public DcMotor liftMotor;

    public boolean lifted = false;

    long t;

    public LiftSystem(OpMode op) {
        super(op);
    }

    public void init() {
        clawServo2 = opMode.hardwareMap.servo.get("clawServo2");
        liftServo1 = opMode.hardwareMap.servo.get("liftServo1");
        liftServo2 = opMode.hardwareMap.servo.get("liftServo2");
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");

        liftServo1.setPosition(0);
        liftServo2.setPosition(1);
    }

    public void moveLift() {
        if (lifted) {
            liftMotor.setPower(power);
            liftServo1.setPosition(0);
            liftServo2.setPosition(1);
        } else {
            liftMotor.setPower(-power);
            liftServo1.setPosition(.22);
            liftServo2.setPosition(.78);
        }
        lifted = !lifted;
        t = System.currentTimeMillis();
    }

    public void loop() {
        if (xGamepad1().a.wasPressed()) {
            moveLift();
        }
        if(System.currentTimeMillis()-t >= 500){
            liftMotor.setPower(0);
        }
        if (xGamepad1().x.isDown()) {
            liftMotor.setPower(power);
        } else if (xGamepad1().y.isDown()) {
            liftMotor.setPower(-power);
        } else {
            liftMotor.setPower(0);
        }
    }
}