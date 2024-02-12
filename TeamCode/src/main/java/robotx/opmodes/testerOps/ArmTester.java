package robotx.opmodes.testerOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.PressHandler;
@TeleOp(name = "ArmTesterOp", group = "Tests")
public class ArmTester extends OpMode {

    Servo testServo1;
    Servo testServo2;

    Servo testServo3;
    Servo testServo4;


    double servoPosition1;
    double servoPosition2;
    double servoPosition3;
    double servoPosition4;

    PressHandler gamepad1_dpad_up;
    PressHandler gamepad1_dpad_down;
    PressHandler gamepad1_dpad_left;
    PressHandler gamepad1_dpad_right;
    PressHandler gamepad1_a;
    PressHandler gamepad1_b;
    PressHandler gamepad1_x;
    PressHandler gamepad1_y;
    PressHandler gamepad1_left_bumper;
    PressHandler gamepad1_right_bumper;

    double unit = 0.01;


    @Override
    public void init() {
        // with new config
        testServo1 = hardwareMap.servo.get("leftWrist"); //up: 0.491
        testServo2 = hardwareMap.servo.get("rightWrist"); //up: 0.670

        testServo3 = hardwareMap.servo.get("leftArm");
        testServo4 = hardwareMap.servo.get("rightArm");

        gamepad1_dpad_up = new PressHandler();
        gamepad1_dpad_down = new PressHandler();
        gamepad1_dpad_left = new PressHandler();
        gamepad1_dpad_right = new PressHandler();
        gamepad1_a = new PressHandler();
        gamepad1_b = new PressHandler();
        gamepad1_y = new PressHandler();
        gamepad1_x = new PressHandler();
        gamepad1_left_bumper = new PressHandler();
        gamepad1_right_bumper = new PressHandler();

        //testServo11.setDirection(Servo.Direction.REVERSE);
        //testServo12.setDirection(Servo.Direction.REVERSE);
        testServo3.setDirection(Servo.Direction.REVERSE);
        //testServo4.setDirection(Servo.Direction.REVERSE);

    }

    @Override
    public void start() {

        // down positions
        servoPosition1 = .14;
        servoPosition2 = .96;
        servoPosition3 = .283;
        servoPosition4 = .703;

    }

    @Override
    public void loop() {

        gamepad1_dpad_up.update(gamepad1.dpad_up);
        gamepad1_dpad_down.update(gamepad1.dpad_down);
        gamepad1_dpad_right.update(gamepad1.dpad_right);
        gamepad1_dpad_left.update(gamepad1.dpad_left);
        gamepad1_a.update(gamepad1.a);
        gamepad1_b.update(gamepad1.b);
        gamepad1_y.update(gamepad1.y);
        gamepad1_x.update(gamepad1.x);
        gamepad1_left_bumper.update(gamepad1.left_bumper);
        gamepad1_right_bumper.update(gamepad1.right_bumper);

        if (gamepad1_b.onPress()) {
            servoPosition4 += unit;
        }
        if (gamepad1_left_bumper.onPress()) {
            unit /= 10;
            if(unit <= 0.0001){
                unit = 0.001;
            }
        }
        if (gamepad1_right_bumper.onPress()) {
            unit *= 10;
            if(unit >= 1) {
            unit = 0.1;
            }
        }
        telemetry.addData("Unit: ", unit);

        if (gamepad1_dpad_up.onPress()) {
            servoPosition1 += unit;
        }
        if (gamepad1_dpad_down.onPress()) {
            servoPosition1 -= unit;
        }
        if (gamepad1_dpad_right.onPress()) {
            servoPosition3 += unit;
        }
        if (gamepad1_dpad_left.onPress()) {
            servoPosition3 -= unit;
        }
        if (gamepad1_y.onPress()) {
            servoPosition2 += unit;
        }
        if (gamepad1_x.onPress()) {
            servoPosition4 -= unit;
        }
        if (gamepad1_a.onPress()) {
            servoPosition2 -= unit;
        }

        if (servoPosition1 > 1.0) {
            servoPosition1 = 1.0;
        }
        if (servoPosition2 > 1.0) {
            servoPosition2 = 1.0;
        }

            if (servoPosition1 < 0.0) {
                servoPosition1 = 0.0;
            }
            if (servoPosition2 < 0.0) {
                servoPosition2 = 0.0;
            }

        testServo1.setPosition(servoPosition1);
        testServo2.setPosition(servoPosition2);
        testServo3.setPosition(servoPosition3);
        testServo4.setPosition(servoPosition4);

        telemetry.addData("Servo Position 1: ", testServo1.getPosition());
        telemetry.addData("Servo Position 2: ", testServo2.getPosition());
        telemetry.addData("Servo Position 3: ", testServo3.getPosition());
        telemetry.addData("Servo Position 4: ", testServo4.getPosition());
    }

    @Override
    public void stop() {

    }

}