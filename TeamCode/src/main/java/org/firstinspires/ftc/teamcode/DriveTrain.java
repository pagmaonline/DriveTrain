
package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.ThreadUtil.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class DriveTrain {
    public enum DriveTrainType {
        NON_HOLONOMIC,
        HOLONOMIC_MECANUM,
        HOLONOMIC_OMNI,
        // KIWI
    }
    private final DriveTrainType driveTrainType;
    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;
    private final double wheelCircumferences;
    private final double encoderPulsesPeRevolution; // 384.5 PPR at the Output Shaft
    private final GoBildaPinpointDriver pinpointDriver;

    private PIDController driveController;
    private PIDController rotationController;



    private final double mecanumEfficiencyReciprocal = 1 / (Math.sqrt(2) / 2); // 1 / Math.sin(Math.toRadians(45)) = 1 / Math.cos(Math.toRadians(45)) == 1 / (Math.sqrt(2) / 2)



    // distance sensors
    // field position


    public DriveTrain(DriveTrainType driveTrainType, DcMotorEx frontLeft, DcMotorEx frontRight, DcMotorEx backLeft, DcMotorEx backRight, double wheelCircumferences, double encoderPulsesPeRevolution, GoBildaPinpointDriver pinpointDriver, PIDController driveController, PIDController rotationController, GoBildaPinpointDriver.EncoderDirection xEncoderDirection, GoBildaPinpointDriver.EncoderDirection yEncoderDirection, GoBildaPinpointDriver.GoBildaOdometryPods encoderResolution, double xEncoderOffset, double yEncoderOffset, boolean keepLastKnownPosition) {
        this.driveTrainType = driveTrainType;
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.wheelCircumferences = wheelCircumferences;
        this.encoderPulsesPeRevolution = encoderPulsesPeRevolution;
        this.pinpointDriver = pinpointDriver;
        this.driveController = driveController;
        this.rotationController = rotationController;
        initOdometry(xEncoderDirection, yEncoderDirection, encoderResolution, xEncoderOffset, yEncoderOffset);
        if (!keepLastKnownPosition) {
            resetPosAndIMU();
        }
    }

    public DriveTrain(DriveTrainType driveTrainType, DcMotorEx frontLeft, DcMotorEx frontRight, DcMotorEx backLeft, DcMotorEx backRight, double wheelCircumferences, double encoderPulsesPeRevolution, GoBildaPinpointDriver pinpointDriver, PIDController driveController, PIDController rotationController, GoBildaPinpointDriver.EncoderDirection xEncoderDirection, GoBildaPinpointDriver.EncoderDirection yEncoderDirection, double encoderResolution, double xEncoderOffset, double yEncoderOffset, boolean keepLastKnownPosition) {
        this.driveTrainType = driveTrainType;
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.wheelCircumferences = wheelCircumferences;
        this.encoderPulsesPeRevolution = encoderPulsesPeRevolution;
        this.pinpointDriver = pinpointDriver;
        this.driveController = driveController;
        this.rotationController = rotationController;
        initOdometry(xEncoderDirection, yEncoderDirection, encoderResolution, xEncoderOffset, yEncoderOffset);
        if (!keepLastKnownPosition) {
            resetPosAndIMU();
        }
    }

    public void initOdometry(GoBildaPinpointDriver.EncoderDirection xEncoderDirection, GoBildaPinpointDriver.EncoderDirection yEncoderDirection, GoBildaPinpointDriver.GoBildaOdometryPods encoderResolution, double xEncoderOffset, double yEncoderOffset) {
        pinpointDriver.setEncoderDirections(xEncoderDirection, yEncoderDirection);
        pinpointDriver.setEncoderResolution(encoderResolution);
        pinpointDriver.setOffsets(xEncoderOffset, yEncoderOffset); // mm
    }

    public void initOdometry(GoBildaPinpointDriver.EncoderDirection xEncoderDirection, GoBildaPinpointDriver.EncoderDirection yEncoderDirection, double encoderResolution, double xEncoderOffset, double yEncoderOffset) {
        pinpointDriver.setEncoderDirections(xEncoderDirection, yEncoderDirection);
        pinpointDriver.setEncoderResolution(encoderResolution);
        pinpointDriver.setOffsets(xEncoderOffset, yEncoderOffset); // mm
    }

    public void resetPosAndIMU() {
        pinpointDriver.resetPosAndIMU(); // takes 0.25 seconds
        sleep(300);
    }



    // inch / sec

    public void driveTo() {

    }

    public void driveAlong() {

    }
    // useing odo or not for tankdrive

    public void tankDrive(double distance, double power) {
        addToTargetPositions(0,0,0,0);
        setMotorRunModes(DcMotorEx.RunMode.RUN_TO_POSITION);
        int encoderPulses =  (int) (((distance / wheelCircumferences) * encoderPulsesPeRevolution) * mecanumEfficiencyReciprocal);
        addToTargetPositions(encoderPulses, encoderPulses, encoderPulses, encoderPulses);
        setMotorPowers(Math.abs(power));
    }

    public void strafeDrive(double distance, double power) {
        addToTargetPositions(0,0,0,0);
        setMotorRunModes(DcMotorEx.RunMode.RUN_TO_POSITION);
        int encoderPulses =  (int) (((distance / wheelCircumferences) * encoderPulsesPeRevolution) * mecanumEfficiencyReciprocal);
        addToTargetPositions(encoderPulses, -encoderPulses, encoderPulses, -encoderPulses);
        setMotorPowers(Math.abs(power));
    }

    public void driveTheta(double distance, double angle, double power) {
        addToTargetPositions(0,0,0,0);
        setMotorRunModes(DcMotorEx.RunMode.RUN_TO_POSITION);
        int encoderPulses =  (int) (((distance / wheelCircumferences) * encoderPulsesPeRevolution) * mecanumEfficiencyReciprocal);
        addToTargetPositions(encoderPulses, -encoderPulses, encoderPulses, -encoderPulses);
        setMotorPowers(Math.abs(power));
    }

    public void turn() {

    }

    public void turnOdo() {

    }

    public void waitUntilMotorsStop() {
        while(frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy());
    }

    public double[] calculateMecanumJacobianMatrix(double x, double y, double r) {
        double frontLeftPower = +y + x - r;
        double frontRightPower = y - x + r;
        double backLeftPower = y - x - r;
        double backRightPower = +y + x + r;

        double maxPower = 0.0;
        for (double power : new double[] { frontLeftPower, frontRightPower, backLeftPower, backRightPower}) {
            maxPower = Math.max(maxPower, Math.abs(power));
        }

        frontLeftPower /= maxPower;
        frontRightPower /= maxPower;
        backLeftPower /= maxPower;
        backRightPower /= maxPower;

        return new double[] { frontLeftPower, frontRightPower, backLeftPower, backRightPower };
    }

    public void setMotorPowers(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }
    public void setMotorPowers(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    public void setMotorRunModes(DcMotor.RunMode runMode){
        frontLeft.setMode(runMode);
        frontRight.setMode(runMode);
        backLeft.setMode(runMode);
        backRight.setMode(runMode);
    }

    public void addToTargetPositions(int frontLeftAddend, int frontRightAddend, int backLeftAddend, int backRightAddend) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + frontLeftAddend);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + frontLeftAddend);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + frontLeftAddend);
        backRight.setTargetPosition(backRight.getCurrentPosition() + frontLeftAddend);
    }

    // this method will move the bot up to 100 inches in any direction to tune pid controllers
    public void autoTunePIDControllers() {



    }

//    public void setAllTargetPositions(int targetPos) {
//
//        public void setTargetPositions(
//
//        public double getMotorSpeed(double speed) {
//
//
//        }
//
//        24in 0.8
//        12in 0.5
//
//
//        Kp = target speed / distance
//
//        speed = feet / sec
//
//                (motor rpm * 60) * wheel circumference (in feet)
//
//        30 ft/sec
//
//        Figure out the motor speed to go 30 ft/sec
//
//                (Desired speed (in ft/sec) * 60) / Wheel circumference (in feet) / motor max rpm
//
//
//
//
//
//        public void regulateMotors() {
//
//            If (lfmotorismoving)
//            Vleociry is < 0.1
//        }
//
//
//        Spline for speed
//
//        Implement constant speed tather than motor speeds
}
