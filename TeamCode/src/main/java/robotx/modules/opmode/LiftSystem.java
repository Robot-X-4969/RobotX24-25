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
    public Servo liftServo3;
    public Servo liftServo4;

    public double power = 1;

    public DcMotor liftMotor1;
    public DcMotor liftMotor2;

    public boolean lifted = true;


    long t;

    public LiftSystem(OpMode op) {
        super(op);
    }

    public void init() {
        liftServo1 = opMode.hardwareMap.servo.get("liftServo1");
        liftServo2 = opMode.hardwareMap.servo.get("liftServo2");
        liftServo3 = opMode.hardwareMap.servo.get("liftServo3");
        liftServo4 = opMode.hardwareMap.servo.get("liftServo4");
        liftMotor1 = opMode.hardwareMap.dcMotor.get("liftMotor1");
        liftMotor2 = opMode.hardwareMap.dcMotor.get("liftMotor2");

        liftServo1.setPosition(.5);
        liftServo2.setPosition(.5);
        liftServo3.setPosition(.5);
        liftServo4.setPosition(.5);
    }

    // sets lift motor power one to the opposite of lift motor one because that's what makes them work
    public void raiseLift(double lift1Power) {
        liftMotor1.setPower(lift1Power);
        liftMotor2.setPower(lift1Power);
    }

    public void moveLift() {
        if (lifted) {
            raiseLift(-power);
            liftServo1.setPosition(0);
            liftServo2.setPosition(1);
            liftServo3.setPosition(0);
            liftServo4.setPosition(1);
        } else {
            raiseLift(power);
            liftServo1.setPosition(.5);
            liftServo2.setPosition(.5);
            liftServo3.setPosition(.5);
            liftServo4.setPosition(.5);
        }
        lifted = !lifted;
        t = System.currentTimeMillis();
    }


    public void loop() {
        if (toggle) {
            if (xGamepad1().a.isDown()) {
                moveLift();
            }
            if (xGamepad1().dpad_up.isDown()) {
                raiseLift(power);
            } else if (xGamepad1().dpad_down.isDown()) {
                raiseLift(-power);
            } else {
                raiseLift(0);
            }
        } else {
            if (xGamepad2().a.isDown()) {
                moveLift();
            }
            if (xGamepad2().dpad_up.isDown()) {
                raiseLift(power);
            } else if (xGamepad2().dpad_down.isDown()) {
                raiseLift(-power);
            } else {
                raiseLift(0);
            }
        }
        if (System.currentTimeMillis() - t >= 500) {
            raiseLift(0);
        }
    }
}