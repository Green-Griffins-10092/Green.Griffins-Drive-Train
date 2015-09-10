package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by David F. on 9/10/2015.
 * To hold the data on the outreach bot base.
 */
public abstract class OutreachBotBase extends OpMode {
    //several named constants
    public static final double STOP_DISTANCE_VALUE = .01;
    public static final double BACKUP_DISTANCE_VALUE = .5;
    //initialize speed variables
    public double leftSpeed;
    public double rightSpeed;
    //declare motors
    DcMotor leftDriveMotor;
    DcMotor rightDriveMotor;
    //declare the distance sensor
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
        }

        if (index > 16) {
            index = 16;
        }

        double dScale;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }
}
