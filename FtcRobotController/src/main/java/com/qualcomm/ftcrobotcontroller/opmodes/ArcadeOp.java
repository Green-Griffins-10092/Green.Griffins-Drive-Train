package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by David F. on 9/8/2015.
 * A simple teleop drive program.
 * Uses arcade control.
 */
public class ArcadeOp extends OutreachBotBase {

    @Override
    public void loop() {
        //initial throttle value
        leftSpeed = -gamepad1.left_stick_y;
        rightSpeed = -gamepad1.left_stick_y;

        //add steering
        leftSpeed += gamepad1.left_stick_x;
        rightSpeed -= gamepad1.left_stick_x;

        //scale input
        leftSpeed = scaleInput(leftSpeed);
        rightSpeed = scaleInput(rightSpeed);

        distanceSensorStop();

        //Set the motors to the corresponding variable
        leftDriveMotor.setPower(leftSpeed);
        rightDriveMotor.setPower(rightSpeed);

        addTelemetryData();
    }
}
