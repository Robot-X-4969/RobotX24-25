package robotx.libraries;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.qualcomm.robotcore.hardware.Servo;

/*
SafeServo Class
Created by John Daniher 01/02/25

Implements servo, yet checks every loop to stop if servo gets stuck
 */

public class SafeServo {
    //Constructor; Requires servo hardware map name and all positions
    public SafeServo(String servoPath, double[] positions){
        this.servoPath = servoPath;
        this.positions = positions;
        lastSetPosition = positions[state];
    }

    public Servo servo;
    public final String servoPath;
    public final double[] positions;

    private double lastSetPosition;
    private double lastPosition;

    private double margin;
    private boolean set = false;

    private int state = 0;

    //Initiates Servo
    public void init(){
        servo = opMode.hardwareMap.servo.get(servoPath);
        servo.setPosition(positions[state]);
    }

    //Cycles forward through positions array
    public void forward(){
        state++;
        if(state >= positions.length){
            state = 0;
        }
        set = false;
        margin = Math.abs(servo.getPosition() - positions[state]);
        servo.setPosition(positions[state]);
    }

    //Cycles backward through positions array
    public void backward(){
        state--;
        if(state < 0){
            state = positions.length-1;
        }
        set = false;
        margin = Math.abs(servo.getPosition() - positions[state]);
        servo.setPosition(positions[state]);
    }

    //Goes to last set servo position
    public void backtrack(){
        servo.setPosition(lastSetPosition);
    }

    //Loops servo; checks progress
    public void loop(){
        if(!set){
            double currentPosition = servo.getPosition();
            //Checks to see if less than 0.1% of progress has been made since last check
            if(Math.abs(currentPosition - lastPosition) < 0.001*margin){
                //Checks to see if within 1% of progress of destination
                if(Math.abs(currentPosition - positions[state]) < 0.01*margin){
                    //Stops servo at current position (to avoid tiny displacements)
                    set = true;
                    lastSetPosition = currentPosition;
                    servo.setPosition(currentPosition);
                } else {
                    //Goes to last set position
                    backtrack();
                }
            }
            //Updates lastposition
            lastPosition = currentPosition;
        }
    }

    //Starts movement to new position
    public void setPosition(double position){
        servo.setPosition(position);
    }
}