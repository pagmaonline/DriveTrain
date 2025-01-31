package org.firstinspires.ftc.teamcode;

public class PIDController {
    private double proportionalCoefficient; // Kp
    private double integralCoefficient; // Ki
    private double derivativeCoefficient; // Kd

    public PIDController() { }
    public PIDController(double proportionalCoefficient, double integralCoefficient, double derivativeCoefficient) {
        this.proportionalCoefficient = proportionalCoefficient;
        this.integralCoefficient = integralCoefficient;
        this.derivativeCoefficient = derivativeCoefficient;
    }

    public double getProportionalCoefficient() {
        return proportionalCoefficient;
    }

    public void setProportionalCoefficient(double proportionalCoefficient) {
        this.proportionalCoefficient = proportionalCoefficient;
    }

    public double getIntegralCoefficient() {
        return integralCoefficient;
    }

    public void setIntegralCoefficient(double integralCoefficient) {
        this.integralCoefficient = integralCoefficient;
    }

    public double getDerivativeCoefficient() {
        return derivativeCoefficient;
    }

    public void setDerivativeCoefficient(double derivativeCoefficient) {
        this.derivativeCoefficient = derivativeCoefficient;
    }
}
