package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.ThreadUtil.runAsync;
import static org.firstinspires.ftc.teamcode.ThreadUtil.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class ChargerRoboticsOpMode extends OpMode {
    Robot robot = new Robot();

    @Override
    public void init() {
        runAsync(() -> {
            while(!robot.isRobotReady()) {
                telemetry.addLine("Please wait .");
                telemetry.update();

                sleep(500);

                telemetry.addLine("Please wait . .");
                telemetry.update();

                sleep(500);

                telemetry.addLine("Please wait . . .");
                telemetry.update();
            }
        });
        robot.init(hardwareMap);
        telemetry.addLine("Ready!");
        telemetry.update();
    }

    @Override
    public void loop() {

    }
}
