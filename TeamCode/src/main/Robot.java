/*
New Official Lancers Robot Class
  Author: Heainz Manoj
  Created in the summer of 2022

Use Instructions


*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.android.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Robot {
    public DcMotorEx frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    public ArrayList<DcMotorEx> driveMotors, leftDriveMotors, rightDriveMotors = new ArrayList<DcMotorEx>();

    public Telemetry telemetry;

    public Robot() {

    }

    public void init(HardwareMap hardwareMap) {
        initializeDriveMotors(hardwareMap, "frontLeftMotor", "frontRightMotor", "backLeftMotor", "backRightMotor");
    }

    private void initializeDriveMotors(HardwareMap hardwareMap, String frontLeft, String frontRight, String backLeft, String backRight) {
        this.frontLeftMotor = hardwareMap.get(DcMotorEx.class, frontLeft);
        driveMotors.add(this.frontLeftMotor);
        this.backLeftMotor = hardwareMap.get(DcMotorEx.class, backLeft);
        driveMotors.add(this.backLeftMotor);
        this.frontRightMotor = hardwareMap.get(DcMotorEx.class, frontRight);
        driveMotors.add(this.frontRightMotor);
        this.backRightMotor = hardwareMap.get(DcMotorEx.class, backRight);
        driveMotors.add(this.backRightMotor);

        leftDriveMotors.add(frontLeftMotor); leftDriveMotors.add(backLeftMotor);
        rightDriveMotors.add(frontRightMotor); rightDriveMotors.add(backRightMotor);

        for (DcMotorEx motor : this.leftDriveMotors) { //Automatically reverse left motors if using NeveRest motors
            if (motor.getDeviceName().toLowerCase().contains("neverest")) {
                motor.setDirection(DcMotorSimple.Direction.REVERSE);
            }
        }

        for (DcMotorEx motor : this.leftDriveMotors) { //Automatically reverse left motors if using NeveRest motors
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        this.say("Drive Motors Initialized");

    }

    public void say(String text) {
        /*
        Lets Android Phone say stuff
        Usage:  robot.say("Hello World");
        */
        AndroidTextToSpeech speaker = new AndroidTextToSpeech();
        speaker.initialize();
        speaker.speak(text);
        speaker.close();
    }

    public void holonomicDrive(double forwardBackward, double strafe, double rotation) {
        //https://gm0.org/en/latest/docs/software/mecanum-drive.html
        double denominator = Math.max(Math.abs(forwardBackward) + Math.abs(strafe) + Math.abs(rotation), 1);
        double frontLeftPower = (forwardBackward + strafe + rotation) / denominator;
        double backLeftPower = (forwardBackward - strafe + rotation) / denominator;
        double frontRightPower = (forwardBackward - strafe - rotation) / denominator;
        double backRightPower = (forwardBackward + strafe - rotation) / denominator;

        this.frontLeftMotor.setPower(frontLeftPower);
        this.backLeftMotor.setPower(backLeftPower);
        this.frontRightMotor.setPower(frontRightPower);
        this.backRightMotor.setPower(backRightPower);
    }

    public void tankDrive(double forwardBackward, double rotation) {
        holonomicDrive(forwardBackward, 0, rotation);
    }
}