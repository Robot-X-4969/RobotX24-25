package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.libraries.XModule;
import robotx.libraries.XServo;

public class ClawSystem extends XModule {

    // motors being used

    public static boolean toggle = true;

    public double increment = 0.066;

    public final XServo clawServo, rotationServo;
    public final XServo[] mountServos;

    public ClawSystem(OpMode op) {
        super(op);
        clawServo = new XServo(op, "clawServo", new double[]{
                0, 0.75
        });
        rotationServo = new XServo(op, "rotationServo", new double[]{
                0, 0.66
        });
        mountServos = new XServo[]{
                new XServo(op, "mountServo1", new double[]{

                }),
                new XServo(op, "mountServo2", new double[]{

                })
        };
    }

    public void init() {
        clawServo.init();
        rotationServo.init();
        mountServos[0].init();
        mountServos[1].init();
    }

    public void loop() {
        if (toggle) {
            if (xGamepad1().a.wasPressed()) {
                rotationServo.setPosition(0.66);
                mountServos[0].forward();
                mountServos[1].forward();
            }
            if (xGamepad1().b.wasPressed()) {
                clawServo.forward();
            }
            if (xGamepad1().dpad_left.isDown()) {
                rotationServo.increment(-increment);
            }
            if (xGamepad1().dpad_right.isDown()) {
                rotationServo.increment(increment);
            }
        } else {
            if (xGamepad2().a.wasPressed()) {
                rotationServo.setPosition(0.66);
                mountServos[0].forward();
                mountServos[1].forward();
            }
            if (xGamepad2().b.wasPressed()) {
                clawServo.forward();
            }
            if (xGamepad2().dpad_left.isDown()) {
                rotationServo.increment(-increment);
            }
            if (xGamepad2().dpad_right.isDown()) {
                rotationServo.increment(increment);
            }
        }
    }
}