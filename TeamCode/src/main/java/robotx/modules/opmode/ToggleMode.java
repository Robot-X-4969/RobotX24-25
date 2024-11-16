package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.libraries.XModule;

public class ToggleMode extends XModule {

    public ToggleMode(OpMode op) {
        super(op);
    }

    public void loop() {
        // button presses, calls methods
        if (xGamepad1().back.wasPressed()) {
        }
    }
}