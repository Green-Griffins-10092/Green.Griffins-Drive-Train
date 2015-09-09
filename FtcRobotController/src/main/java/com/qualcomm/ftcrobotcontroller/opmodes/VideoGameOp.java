package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by David F. on 9/8/2015.
 * A simple teleop drive program.
 */
public class VideoGameOp extends OpMode {

    //initialize speed variables
    public double leftSpeed;
    public double rightSpeed;
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
        if (distanceSensor.getLightDetected() >= .01) {
            leftSpeed = Range.clip(leftSpeed, -1, 0);
            rightSpeed = Range.clip(rightSpeed, -1, 0);
        }
        if (distanceSensor.getLightDetected() >= .75) {
            leftSpeed = -.5;
            rightSpeed = -.5;
        }

        //Set the motors to the corresponding variable
        leftDriveMotor.setPower(leftSpeed);
        rightDriveMotor.setPower(rightSpeed);

        //send telemetry
        telemetry.addData("1. left motor", leftSpeed);
        telemetry.addData("1b. left joystick y", gamepad1.left_stick_y);
        telemetry.addData("2. right motor", rightSpeed);
        telemetry.addData("3. distance sensor reading", distanceSensor.getLightDetected());
        telemetry.addData("4. distance sensor status", distanceSensor.status());
    }

    /*
     * This method scales the joystick input so for low joystick values, the
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */
    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }
}
