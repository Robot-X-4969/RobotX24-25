package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;


public class ArmSystem extends XModule {
    public static boolean toggle = true;
    public static boolean runLoop = true;
    //boolean toggle = true;
    //var setup
    public Servo leftArm;
    public Servo rightArm;
    public Servo leftWrist;
    public Servo rightWrist;
    public Servo blockServo;


    double rightArmPos = .703;
    double leftArmPos = .283;

    double rightWristPos = .96;
    double leftWristPos = .14;

    long t;

    boolean armDown = true; //so arm system knows it starts down
    boolean wrist = true;
    boolean blocked = true;


    int k = 1;
    //methods are built into one button as a toggle

    public void moveArm() {
        if (k == 0) {
            //DOWN
            leftArm.setPosition(leftArmPos);
            rightArm.setPosition(rightArmPos);
            leftWrist.setPosition(leftWristPos);
            rightWrist.setPosition(rightWristPos);

            blockServo.setPosition(.6);
            k++;
        }
        else if (k == 1){
            leftWrist.setPosition(.84);
            rightWrist.setPosition(.167);
            leftArm.setPosition(.48);
            rightArm.setPosition(.59);
            k++;
        }
        else{
            //UP
            leftWrist.setPosition(1.0);
            rightWrist.setPosition(0.10);
            leftArm.setPosition(.517);
            rightArm.setPosition(0.537);


            blockServo.setPosition(.6);
            k=k-2;
        }
    }
    public void autonMoveArm() {
        //dont touch without asking cstaut pls
        if (k == 0) {
            leftArm.setPosition(0.48);
            rightArm.setPosition(0.59);
            leftWrist.setPosition(.84);
            rightWrist.setPosition(.167);
            armDown = false;
        } else {
            leftArm.setPosition(leftArmPos);
            rightArm.setPosition(rightArmPos);
            leftWrist.setPosition(leftWristPos);
            rightWrist.setPosition(rightWristPos);
            armDown = true;
        }
    }

    public void autonToggleBlock() {
        if (blocked) {
            blockServo.setPosition(.1); //unblock
            blocked = false;
        } else {
            blockServo.setPosition(.6);
            blocked = true;
        }
    }

    public void toggleBlock() {
        if (!blocked) {
            blockServo.setPosition(.1);
            blocked = true;
        }
        else {
            blockServo.setPosition(.6);
            blocked = false;
        }
    }

    public void release()
    {
        blockServo.setPosition(.1);
    }



    public ArmSystem(OpMode op) {
        super(op);
    }
    public void init() {
        // pulls servos from configs
        leftArm = opMode.hardwareMap.servo.get("leftArm");
        rightArm = opMode.hardwareMap.servo.get("rightArm");
        leftWrist = opMode.hardwareMap.servo.get("leftWrist");
        rightWrist = opMode.hardwareMap.servo.get("rightWrist");
        blockServo = opMode.hardwareMap.servo.get("blockServo");

        leftArm.setDirection(Servo.Direction.REVERSE);
        rightArm.setDirection(Servo.Direction.REVERSE);

        leftWrist.setPosition(leftWristPos);
        rightWrist.setPosition(rightWristPos);

        leftArm.setPosition(leftArmPos);
        rightArm.setPosition(rightArmPos);

        blockServo.setPosition(.6);
    }

    public void loop() {
        if (runLoop) {
            // button presses, calls methods
            /*

             */
            if (k == 1) {
                leftWrist.setPosition(leftWristPos);
                rightWrist.setPosition(rightWristPos);
                leftArm.setPosition(leftArmPos);
                rightArm.setPosition(rightArmPos);
            }
            if (toggle) {
                if (xGamepad1().b.wasPressed()) {
                    release();
                    t = System.currentTimeMillis();
                }
                if (System.currentTimeMillis() - t > 1500) {
                    blockServo.setPosition(.6);
                }
                if (xGamepad1().a.wasPressed()) {
                    moveArm();
                }
            } else {
                if (xGamepad2().b.wasPressed()) {
                    release();
                    t = System.currentTimeMillis();
                }
                if (System.currentTimeMillis() - t > 1500) {
                    blockServo.setPosition(.6);
                }
                if (xGamepad2().y.wasPressed()) {
                    leftWrist.setPosition(leftWristPos);
                    rightWrist.setPosition(rightWristPos);
                    leftArm.setPosition(leftArmPos);
                    rightArm.setPosition(rightArmPos);
                    k = 0;
                }
                if (xGamepad2().a.wasPressed()) {
                    moveArm();
                }
            }
        }
    }
}