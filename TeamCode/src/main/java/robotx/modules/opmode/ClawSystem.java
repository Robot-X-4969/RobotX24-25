package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class ClawSystem extends XModule {

    // motors being used

    public static boolean toggle = true;

    public Servo clawServo1;
    public Servo clawServo2;

    public boolean clawed = false;

    public ClawSystem(OpMode op) {
        super(op);
    }
    public void init() {
        clawServo1 = opMode.hardwareMap.servo.get("clawServo1");
        clawServo2 = opMode.hardwareMap.servo.get("clawServo2");

        clawServo1.setPosition(0);
        clawServo2.setPosition(1);
    }

    public void moveClaw(){
        if(clawed){
            clawServo1.setPosition(0);
            clawServo2.setPosition(1);
        } else {
            clawServo1.setPosition(.22);
            clawServo2.setPosition(.78);
        }
        clawed = !clawed;
    }

    public void loop() {
        if(xGamepad1().a.wasPressed()){
            moveClaw();
        }
    }
}