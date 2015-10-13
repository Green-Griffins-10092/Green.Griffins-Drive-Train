package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by David F. on 9/8/2015.
 * A simple teleop drive program.
 * Uses "video game controls," similar to racing games.
 * The triggers control speed, and the left stick controls direction.
 */
public class VideoGameOp extends OutreachBotBase {

    @Override
    public void loop() {
        //initial throttle value
        leftSpeed = gamepad1.right_trigger - gamepad1.left_trigger;
        rightSpeed = gamepad1.right_trigger - gamepad1.left_trigger;

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

        addTelemetryData();
    }
}
