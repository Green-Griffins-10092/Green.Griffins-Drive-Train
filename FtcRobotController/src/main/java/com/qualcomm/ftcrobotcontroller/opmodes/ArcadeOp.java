package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by David F. on 9/8/2015.
 * A simple teleop drive program.
 * Uses arcade control.
 */
public class ArcadeOp extends OutreachBotBase {

    private boolean locked = false;

    @Override
    public void loop() {

        if (gamepad2.b) {
            locked = !locked;
        }

        if (locked) {
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

            //if the front motors exist, use them
            if (doFrontMotorsExist()) {
                leftFrontDriveMotor.setPower(leftSpeed);
                rightFrontDriveMotor.setPower(rightSpeed);
            }
        } else {
            //initial throttle value
            leftSpeed = -gamepad2.left_stick_y;
            rightSpeed = -gamepad2.left_stick_y;

            //add steering
            leftSpeed += gamepad2.left_stick_x;
            rightSpeed -= gamepad2.left_stick_x;

            //Set the motors to the corresponding variable
            leftDriveMotor.setPower(leftSpeed);
            rightDriveMotor.setPower(rightSpeed);
        }
        addTelemetryData();
    }
}
