package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class GrapplingHook extends XModule {


    // motors being used
    public static boolean toggle = true;
    public DcMotor GrappleMotor;
    public Servo Stabilizer;

    double power = 1;
    boolean stabilized = false;
    boolean motor = false;

    public GrapplingHook(OpMode op) {
        super(op);
    }

    public void init() {

        //init motors from configs

        GrappleMotor = opMode.hardwareMap.dcMotor.get("GrappleMotor");
        Stabilizer = opMode.hardwareMap.servo.get("Stabilizer");

        Stabilizer.setPosition(.56);
    }

    public void loop() {
        if(toggle) {
            GrappleMotor.setPower(0);
            if (xGamepad1().left_bumper.wasPressed()) {
                motor = !motor;
                Stabilizer.setPosition(.56);
            }
            if (motor) {
                GrappleMotor.setPower(-power);
            }
            if (xGamepad1().right_bumper.isDown()) {
                GrappleMotor.setPower(power);
                Stabilizer.setPosition(.05);
            }
        } else {
            if (xGamepad2().left_bumper.wasPressed()){
                motor = !motor;
            } else {
                GrappleMotor.setPower(0.0);
                if(motor){
                    GrappleMotor.setPower(-power);
                }
                if(xGamepad2().right_bumper.isDown()) {
                    GrappleMotor.setPower(power);
                }
            }
        }
    }// stabilizer .26 up, .8 down

}