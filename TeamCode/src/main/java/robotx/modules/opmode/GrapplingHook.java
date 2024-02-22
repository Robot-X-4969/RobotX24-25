package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class GrapplingHook extends XModule {


    // motors being used
    public static boolean toggle = true;
    public DcMotor GrappleMotor;

    double power = 1;
    boolean motor = false;

    public GrapplingHook(OpMode op) {
        super(op);
    }

    public void init() {

        //init motors from configs

        GrappleMotor = opMode.hardwareMap.dcMotor.get("GrappleMotor");
    }

    public void loop() {
        if(toggle) {
            if(xGamepad1().left_bumper.isDown()){
                GrappleMotor.setPower(1);
            } else if(xGamepad1().right_bumper.isDown()){
                GrappleMotor.setPower(-0.5);
            } else {
                GrappleMotor.setPower(0);
            }
        } else {
            if(xGamepad2().left_bumper.isDown()){
                GrappleMotor.setPower(1);
            } else if(xGamepad2().right_bumper.isDown()){
                GrappleMotor.setPower(-0.5);
            } else {
                GrappleMotor.setPower(0);
            }
        }

    }// stabilizer .26 up, .8 down

}