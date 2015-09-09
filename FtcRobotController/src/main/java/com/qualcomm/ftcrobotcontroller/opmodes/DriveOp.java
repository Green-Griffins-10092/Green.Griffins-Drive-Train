package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by David F. on 9/3/2015.
 * A simple teleop drive program.
 *
 * Edited on:
 * 9/7/15, Added telemetry, tested on robot, need to reverse motor direction.
 */
public class DriveOp extends OpMode {

    //declare motors
    DcMotor leftDriveMotor;
    DcMotor rightDriveMotor;
    OpticalDistanceSensor distanceSensor;


    @Override
    public void init() {
        //get the motors from the hardware map named "left_motor" and "right_motor" without quotations.
        leftDriveMotor = hardwareMap.dcMotor.get("left_motor");
        rightDriveMotor = hardwareMap.dcMotor.get("right_motor");
        //reverse a motor so that both motors drive in the same direction
        leftDriveMotor.setDirection(DcMotor.Direction.REVERSE);

        //get distance sensor
        distanceSensor = hardwareMap.opticalDistanceSensor.get("distance_sensor");
        distanceSensor.enableLed(true);
    }

    @Override
    public void loop() {
        //create variables for motor values
        double leftPower = -gamepad1.left_stick_y;
        double rightPower = -gamepad1.right_stick_y;

        //do not move forward if light sensor readings are high
        if (distanceSensor.getLightDetected() >= .05) {
            leftPower = Range.clip(-1, 0, leftPower);
            rightPower = Range.clip(-1, 0, rightPower);
        }
        if (distanceSensor.getLightDetected() >= .75) {
            leftPower = -.5;
            rightPower = -.5;
        }

        //Set the motors to the corresponding variable
        leftDriveMotor.setPower(leftPower);
        rightDriveMotor.setPower(rightPower);

        //send telemetry
        telemetry.addData("1. left motor", leftPower);
        telemetry.addData("1b. left joystick y", gamepad1.left_stick_y);
        telemetry.addData("2. right motor", rightPower);
        telemetry.addData("3. distance sensor reading", distanceSensor.getLightDetected());
        telemetry.addData("4. distance sensor status", distanceSensor.status());
    }
}
