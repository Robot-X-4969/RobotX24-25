package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class ClawSystem extends XModule {

    // motors being used

    public static boolean toggle = true;

    public Servo clawServo;
    public Servo rotationServo;

    public double rotation = 0.66;
    public double increment = 0.005;

    public boolean open = false;

    public boolean lifted = true;

    public ClawSystem(OpMode op) {
        super(op);
    }

    public void init() {
        clawServo = opMode.hardwareMap.servo.get("clawServo");
        rotationServo = opMode.hardwareMap.servo.get("rotationServo");

        rotationServo.setPosition(0);
        clawServo.setPosition(0);
    }

    public void claw() {
        if (open) {
            clawServo.setPosition(0);
        } else {
            clawServo.setPosition(0.75);
        }
        open = !open;
    }

    public void moveOnlift() {
        rotationServo.setPosition(0.66);
    }

    public void loop() {
        if (toggle) {
            if (xGamepad1().a.wasPressed()) {
                moveOnlift();
            }
            if (xGamepad1().b.wasPressed()) {
                claw();
            }
            if (xGamepad1().dpad_left.isDown()) {
                rotation -= increment;
                if (rotation < 0) {
                    rotation = 0;
                }
            }
            if (xGamepad1().dpad_right.isDown()) {
                rotation += increment;
                if (rotation > 1) {
                    rotation = 1;
                }
            }
        } else {
            if (xGamepad2().a.wasPressed()) {
                moveOnlift();
            }
            if (xGamepad2().b.wasPressed()) {
                claw();
            }
            if (xGamepad2().dpad_left.isDown()) {
                rotation -= increment;
                if (rotation < 0) {
                    rotation = 0;
                }
            }
            if (xGamepad2().dpad_right.isDown()) {
                rotation += increment;
                if (rotation > 1) {
                    rotation = 1;
                }
            }
        }
        rotationServo.setPosition(rotation);
    }
}