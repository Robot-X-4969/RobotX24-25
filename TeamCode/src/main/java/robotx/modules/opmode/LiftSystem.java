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

    public DcMotor liftMotor1;
    public DcMotor liftMotor2;

    public boolean lifted = false;

    long t;

    public LiftSystem(OpMode op) {
        super(op);
    }

    public void init() {
        liftServo1 = opMode.hardwareMap.servo.get("liftServo1");
        liftServo2 = opMode.hardwareMap.servo.get("liftServo2");
        liftMotor1 = opMode.hardwareMap.dcMotor.get("liftMotor1");
        liftMotor2 = opMode.hardwareMap.dcMotor.get("liftMotor2");

        liftServo1.setPosition(0);
        liftServo2.setPosition(1);
    }

    // sets lift motor power one to the opposite of lift motor one because that's what makes them work
    public void raiseLift(double lift1Power) {
        liftMotor1.setPower(lift1Power);
        liftMotor2.setPower(-lift1Power);
    }

    public void moveLift() {
        if (lifted) {
            raiseLift(power);
            liftServo1.setPosition(0);
            liftServo2.setPosition(1);
        } else {
            raiseLift(-power);
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
            raiseLift(0);
        }
        if (xGamepad1().x.isDown()) {
            raiseLift(power);
        } else if (xGamepad1().y.isDown()) {
            raiseLift(-power);
        } else {
            raiseLift(0);
        }
    }
}