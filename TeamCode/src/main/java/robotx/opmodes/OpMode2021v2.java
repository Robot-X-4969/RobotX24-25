package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.opmode.ClawSystem;
import robotx.modules.opmode.IntakeSystem;
import robotx.modules.opmode.OrientationDrive;


// sample change


@TeleOp(name = "OpMode 23-24", group = "CurrentOp")
public class OpMode2021v2 extends XOpMode {
    OrientationDrive orientationDrive;
    ClawSystem clawSystem;

    //ToggleMode toggleMode;

    public void initModules() {

        super.initModules();

        orientationDrive = new OrientationDrive(this);
        activeModules.add(orientationDrive);

        clawSystem = new ClawSystem(this);
        activeModules.add(clawSystem);
    }

    public void init() {
        super.init();
    } {

    }
    /*
    @Override
    public void start() {

    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {

    }
    */

}

/*
Controls
GamePad 1:

Gamepad 2:

*/