package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by David F. on 9/10/2015.
 * To hold the data on the outreach bot base.
 */
public abstract class OutreachBotBase extends OpMode {
    //several named constants
    protected static final double STOP_DISTANCE_VALUE = .01;
    protected static final double BACKUP_DISTANCE_VALUE = .5;

    //initialize speed variables
    protected double leftSpeed;
    protected double rightSpeed;

    //declare motors
    protected DcMotor leftDriveMotor;
    protected DcMotor rightDriveMotor;
    protected DcMotor rightFrontDriveMotor;
    protected DcMotor leftFrontDriveMotor;

    //declare the distance sensor
    protected OpticalDistanceSensor distanceSensor;

    //in case the sensor does not exist, use fail safe
    private boolean sensorsExist;

    public boolean doFrontMotorsExist() {
        return frontMotorsExist;
    }

    private boolean frontMotorsExist;

    protected boolean locked;

    //Set up motors and sensors
    @Override
    public void init() {
        //get the motors from the hardware map named "left_motor" and "right_motor" without quotations.
        leftDriveMotor = hardwareMap.dcMotor.get("left_motor");
        rightDriveMotor = hardwareMap.dcMotor.get("right_motor");
        //reverse a motor so that both motors drive in the same direction
        rightDriveMotor.setDirection(DcMotor.Direction.REVERSE);

        try {
            //get the motors from the hardware map named "left_motor" and "right_motor" without quotations.
            leftFrontDriveMotor = hardwareMap.dcMotor.get("left_front_motor");
            rightFrontDriveMotor = hardwareMap.dcMotor.get("right_front_motor");
            //reverse a motor so that both motors drive in the same direction
            leftDriveMotor.setDirection(DcMotor.Direction.REVERSE);
            frontMotorsExist = true;
        } catch (IllegalArgumentException e) {
            frontMotorsExist = false;
        }

        //if the sensor does not exist, proceed with out it
        try {
            //get distance sensor
            distanceSensor = hardwareMap.opticalDistanceSensor.get("distance_sensor");
            distanceSensor.enableLed(true);
            sensorsExist = true;
        } catch (IllegalArgumentException e) {
            sensorsExist = false;
        }

        locked = false;
    }

    /*
     * This method scales the joystick input so for low joystick values, the
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 * (Based off the scaleInput method from K9TeleOp)
	 */
    protected double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        //ensure that the index will be in range
        if (index < 0) {
            index = -index;
        }
        if (index > 16) {
            index = 16;
        }

        //make sure the sign is right
        double dScale;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }

    //This method returns whether the sensors exist
    protected boolean doSensorsExist() {
        return sensorsExist;
    }

    //Add the telemetry data
    protected void addTelemetryData() {
        //send telemetry
        telemetry.addData("left motor", leftSpeed);
        telemetry.addData("right motor", rightSpeed);
        telemetry.addData("front motor status", frontMotorsExist);
        telemetry.addData("sensor status", doSensorsExist());
        telemetry.addData("locked status", locked);
        //if sensors exist
        if (doSensorsExist()) {
            telemetry.addData("distance sensor reading", distanceSensor.getLightDetected());
            telemetry.addData("distance sensor status", distanceSensor.status());
        }
    }

    //distance sensor stop
    public void distanceSensorStop() {
        if (doSensorsExist()) {
            //do not move forward if light sensor readings are high
            if (distanceSensor.getLightDetected() >= STOP_DISTANCE_VALUE) {
                leftSpeed = Range.clip(leftSpeed, -1, 0);
                rightSpeed = Range.clip(rightSpeed, -1, 0);
            }
            if (distanceSensor.getLightDetected() >= BACKUP_DISTANCE_VALUE) {
                leftSpeed = -.5;
                rightSpeed = -.5;
            }
        }
    }
}
