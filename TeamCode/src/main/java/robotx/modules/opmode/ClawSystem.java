package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class ClawSystem extends XModule {

    // motors being used

    public static boolean toggle = true;

    public Servo clawServo;

    public boolean clawed = false;

    public ClawSystem(OpMode op) {
        super(op);
    }
    public void init() {
        clawServo = opMode.hardwareMap.servo.get("clawServo");

        clawServo.setPosition(0);
    }

    public void moveClaw(){
        if(clawed){
            clawServo.setPosition(0.25);
        } else {
            clawServo.setPosition(0.5);
        }
        clawed = !clawed;
    }

    public void loop() {
        if(xGamepad1().a.wasPressed()){
            moveClaw();
        }
    }
}