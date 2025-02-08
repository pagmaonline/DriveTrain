package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public abstract class LinearSlide implements DcMotorEx {
    private double stageLength;
    private int stages;
    private int ticksPerInch;

    public LinearSlide(double stageLength, int stages, int ticksPerInch) {
        this.stageLength = stageLength;
        this.stages = stages;
        this.ticksPerInch = ticksPerInch;
    }

    public void extend(double length) {

    }

    public void retract() {

    }

    public void positionTo() {

    }

    public void retractToStall() {

    }


}
