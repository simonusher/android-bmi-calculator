package com.example.szymon.bmicalculator;

/**
 * Created by Szymon on 11.03.2018.
 */

public class BmiForLbIn extends Bmi {
    public static final int MULTIPLIER_LB_IN = 703;

    public BmiForLbIn() {
    }

    public BmiForLbIn(double mass, double heigth) {
        super(mass, heigth);
    }

    @Override
    public double calculateBmi() {
        return super.calculateBmi() * MULTIPLIER_LB_IN;
    }
}
