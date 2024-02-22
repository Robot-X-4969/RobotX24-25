package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class IntakeSystem extends XModule {

    // motors being used

    public static boolean toggle = true;

    public DcMotor IntakeMotor;
    public Servo leftIntake;
    public Servo rightIntake;


    double power = 0.7;

    public IntakeSystem(OpMode op) {
        super(op);
    }

    public void init() { 

        //init motors from configs

        IntakeMotor = opMode.hardwareMap.dcMotor.get("IntakeMotor");
        leftIntake = opMode.hardwareMap.servo.get("leftIntake");
        rightIntake = opMode.hardwareMap.servo.get("rightIntake");
    }

    public void loop() {
        leftIntake.setPosition(.301);
        rightIntake.setPosition(.738);
        if (toggle) {
            if (xGamepad1().x.isDown()) {
                IntakeMotor.setPower(power);
            } else if (xGamepad1().y.isDown()) {
                IntakeMotor.setPower(-power);
            } else {
                IntakeMotor.setPower(0);
            }
        } else {
            if(xGamepad2().right_trigger > .25) {
                IntakeMotor.setPower(power);
            }
            else if(xGamepad2().left_trigger > .25) {
                IntakeMotor.setPower(-power);
            }
            else{
                IntakeMotor.setPower(0);
            }
        }
    }
}