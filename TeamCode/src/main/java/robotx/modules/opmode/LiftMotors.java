package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.XModule;

public class LiftMotors extends XModule {


    // motors being used

    public static boolean toggle = true;

    public DcMotor LeftLift;
    public DcMotor RightLift;

    double power = 1;
    double power2 = 1;

    public LiftMotors(OpMode op) {
        super(op);
    }

    public void init() {

        //init motors from configs

        LeftLift = opMode.hardwareMap.dcMotor.get("leftLift");
        RightLift = opMode.hardwareMap.dcMotor.get("rightLift");

    }

    public void loop() {

        if (toggle) {
            if (xGamepad1().left_trigger > .5) {
                LeftLift.setPower(-xGamepad1().left_trigger);
                RightLift.setPower(xGamepad1().left_trigger);
            } else if (xGamepad1().right_trigger > .5) {
                LeftLift.setPower(xGamepad1().right_trigger);
                RightLift.setPower(-xGamepad1().right_trigger);
            } else {
                LeftLift.setPower(0.01);
                RightLift.setPower(-0.01);
            }
        } else {
            if (xGamepad2().dpad_up.isDown()){
                LeftLift.setPower(power);
                RightLift.setPower(-power);
            }
            else if (xGamepad2().dpad_down.isDown()) {
                LeftLift.setPower(-power2);
                RightLift.setPower(power2);
            }
            else if (xGamepad2().dpad_right.isDown()){
                LeftLift.setPower(0.3);
                RightLift.setPower(-0.3);
            }
            else if (xGamepad2().dpad_left.isDown()) {
                LeftLift.setPower(-0.3);
                RightLift.setPower(0.3);
            }
            /*else if (xGamepad2().left_stick_y != 0){
                LeftLift.setPower(-xGamepad2().left_stick_y);
                RightLift.setPower(xGamepad2().left_stick_y);
            }*/
            else {
                LeftLift.setPower(0.01);
                RightLift.setPower(-0.01);
            }
        }
    }
}

/*
a - all motors same direction
b - all motors opposite direction
 */