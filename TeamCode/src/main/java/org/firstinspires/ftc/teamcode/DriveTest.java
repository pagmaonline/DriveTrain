package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name="Drive test", group="Auto")
public class DriveTest extends ChargerRoboticsOpMode {
    @Override
    public void loop() {
        robot.getDriveTrain().tankDrive(24, 0.1);
        robot.getDriveTrain().waitUntilMotorsStop();
        stop();

    }

}
