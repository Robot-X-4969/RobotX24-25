package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class ClawSystem extends XModule {

    // motors being used

    public static boolean toggle = true;

    public Servo clawServo;

    public boolean open = false;


    long t;

    public ClawSystem(OpMode op) {
        super(op);
    }

    public void init() {
        clawServo = opMode.hardwareMap.servo.get("clawServo");

        clawServo.setPosition(0);
    }

    public void claw(){
        if(open){
            clawServo.setPosition(0);
        } else {
            clawServo.setPosition(0.75);
        }
        open = !open;
    }
    
    public void loop() {
        if(xGamepad1().b.wasPressed()){
            claw();
        }
    }
}