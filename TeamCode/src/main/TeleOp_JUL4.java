package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
TeleOp developed for the July 4th 2022 event by Heainz Manoj
4 Omni Wheel Motors
2 Intake Motors
1 Camera
1 Controller
* */

@TeleOp(name = "July 4th TeleOp", group = "TeleOp")

public class TeleOp_JUL4 extends LinearOpMode {

    Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.say("Running Telecontrolled Operation");

        robot.init(hardwareMap);

        double forwardBackwardStick, strafeLeftRightStick, turnLeftRightStick;

        waitForStart();
        if (isStopRequested()) return;

        robot.say("TeleOp is go");

        while(opModeIsActive()) {
            //Adjust as per driver preference
            strafeLeftRightStick = gamepad1.left_stick_x;
            turnLeftRightStick = gamepad1.right_stick_x;
            forwardBackwardStick = gamepad1.left_stick_y;

            robot.holonomicDrive(-forwardBackwardStick, strafeLeftRightStick, turnLeftRightStick);
        }
    }
}
