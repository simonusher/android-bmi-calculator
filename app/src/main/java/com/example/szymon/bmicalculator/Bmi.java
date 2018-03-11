package com.example.szymon.bmicalculator;

import android.graphics.Color;

/**
 * Created by Szymon on 11.03.2018.
 */

public abstract class Bmi {
    public static final double BMI_UNDERWEIGHT = 18.5;
    public static final double BMI_NORMAL = 25;
    public static final double BMI_OVERWEIGHT = 30;
    protected double mass;
    protected double heigth;

    public Bmi() {
    }

    public Bmi(double mass, double heigth) {
        this.mass = mass;
        this.heigth = heigth;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getHeigth() {
        return heigth;
    }

    public void setHeigth(double heigth) {
        this.heigth = heigth;
    }

    public boolean dataIsValid(){
        return mass > 0 && heigth > 0;
    }

    public double calculateBmi(){
        if(dataIsValid()){
            return mass / (heigth * heigth);
        } else {
            throw new IllegalArgumentException("Data is not valid");
        }
    }

    public int getColorForBmi(double bmi) {
        if (bmi < BMI_UNDERWEIGHT) {
            return Color.RED;
        } else if (bmi <= BMI_NORMAL) {
            return Color.GREEN;
        } else if (bmi <= BMI_OVERWEIGHT) {
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }
}
