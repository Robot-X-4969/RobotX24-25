package robotx.libraries;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 XMotor Class
 Use this class to better incorporate encoders into motor usage.

 Created by John Daniher 01/09/25
 */

public class XMotor {
    //Constructor; no position range set
    public XMotor(OpMode op, String motorPath){
        this.op = op;
        this.motorPath = motorPath;
        minPosition = Integer.MIN_VALUE;
        maxPosition = Integer.MAX_VALUE;
    };

    //Constructor; position range set
    public XMotor(OpMode op, String motorPath, int minPosition, int maxPosition){
        this.op = op;
        this.motorPath = motorPath;
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
    };

    private DcMotor motor;
    private final OpMode op;
    private final String motorPath;

    private boolean set = true;
    private boolean timer = false;
    private long stopTime = 0;

    public int target = 0;
    public int position = 0;
    public double power = 0;

    private int minPosition;
    private int maxPosition;

    //Initialize and map motor
    public void init(){
        motor = op.hardwareMap.dcMotor.get(motorPath);
        reset();
    }

    //Resets encoders
    public void reset(){
        //Resets encoder values
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //Sets the power of the motor to move until stopped
    public void setPower(double power){
        this.power = power;
        motor.setPower(power);
    }

    //Sets the motor to rotate for a given time
    public void turnForTime(int milliseconds, double power){
        setPower(power);
        //Schedules stop
        stopTime = System.currentTimeMillis() + milliseconds;
    }

    //Stops the motor
    public void stop(){
        power = 0;
        target = motor.getCurrentPosition();
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(0);
        set = true;
    }

    //Sets the target position of the motor and begins moving
    public void setPosition(int target){
        this.target = target;
        motor.setPower(power);

        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        set = false;
    }

    //Sets the minimum position
    public void setMinPosition(int minPosition){
        this.minPosition = minPosition;
    }

    //Sets the maximum position
    public void setMaxPosition(int maxPosition){
        this.maxPosition = maxPosition;
    }

    //Loop function; refreshes position variable, ensures within range, etc.
    public void loop(){
        position = motor.getCurrentPosition();
        //If reached position, stop motor
        if(!set){
            if(!motor.isBusy()){
                stop();
            } else if(System.currentTimeMillis() > stopTime){
                stop();
            }
        }

        //Ensures both current position and target position are within range
        if(position < minPosition || target < minPosition){
            setPosition(minPosition);
        } else if(position > maxPosition || target > maxPosition){
            setPosition(maxPosition);
        }
    }
}