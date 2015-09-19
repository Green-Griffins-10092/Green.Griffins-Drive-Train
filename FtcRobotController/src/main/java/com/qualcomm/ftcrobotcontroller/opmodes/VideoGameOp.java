package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.util.Range;

/**
 * Created by David F. on 9/8/2015.
 * A simple teleop drive program.
 * Uses "video game controls," similar to racing games.
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

        //do not move forward if light sensor readings are high
        if (distanceSensor.getLightDetected() >= STOP_DISTANCE_VALUE) {
            leftSpeed = Range.clip(leftSpeed, -1, 0);
            rightSpeed = Range.clip(rightSpeed, -1, 0);
        }
        if (distanceSensor.getLightDetected() >= BACKUP_DISTANCE_VALUE) {
            leftSpeed = -.5;
            rightSpeed = -.5;
        }

        //Set the motors to the corresponding variable
        leftDriveMotor.setPower(leftSpeed);
        rightDriveMotor.setPower(rightSpeed);

        //send telemetry
        telemetry.addData("1. left motor", leftSpeed);
        telemetry.addData("2. right motor", rightSpeed);
        telemetry.addData("3. distance sensor reading", distanceSensor.getLightDetected());
        telemetry.addData("4. distance sensor status", distanceSensor.status());
    }
}
