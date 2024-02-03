package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.libraries.XModule;
import robotx.modules.opmode.ArmSystem;
import robotx.modules.opmode.DroneLaunch;
import robotx.modules.opmode.GrapplingHook;
import robotx.modules.opmode.IntakeSystem;
import robotx.modules.opmode.LiftMotors;

public class ToggleMode extends XModule {

    public ToggleMode(OpMode op) {
        super(op);
    }

    public void loop() {
        // button presses, calls methods
        if (xGamepad1().back.wasPressed()) {
            ArmSystem.toggle = !ArmSystem.toggle;
            DroneLaunch.toggle = !DroneLaunch.toggle;
            GrapplingHook.toggle = !GrapplingHook.toggle;
            IntakeSystem.toggle = !IntakeSystem.toggle;
            LiftMotors.toggle = !LiftMotors.toggle;
        }
    }
}