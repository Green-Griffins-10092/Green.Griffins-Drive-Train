package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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


    @Override
    public void init() {
        //get the motors from the hardware map named "left_motor" and "right_motor" without quotations.
        leftDriveMotor = hardwareMap.dcMotor.get("left_motor");
        rightDriveMotor = hardwareMap.dcMotor.get("right_motor");
        //reverse a motor so that both motors drive in the same direction
        rightDriveMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        //Set the motors to the value of the y axis on the appropriate stick.
        leftDriveMotor.setPower(-gamepad1.left_stick_y);
        rightDriveMotor.setPower(-gamepad1.right_stick_y);

        //send telemetry
        telemetry.addData("left motor:", gamepad1.left_stick_y);
        telemetry.addData("right motor:", gamepad1.right_stick_y);

    }
}
