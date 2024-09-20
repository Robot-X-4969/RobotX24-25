package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.opmode.LiftSystem;
import robotx.modules.opmode.OrientationDrive;


// sample change


@TeleOp(name = "OpMode 23-24", group = "CurrentOp")
public class OpMode2021v2 extends XOpMode {
    OrientationDrive orientationDrive;
    LiftSystem liftSystem;

    //ToggleMode toggleMode;

    public void initModules() {

        super.initModules();

        orientationDrive = new OrientationDrive(this);
        activeModules.add(orientationDrive);

        liftSystem = new LiftSystem(this);
        activeModules.add(liftSystem);
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