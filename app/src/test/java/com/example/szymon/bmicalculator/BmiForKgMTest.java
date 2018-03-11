package com.example.szymon.bmicalculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class BmiForKgMTest {
    @Test(expected = IllegalArgumentException.class)
    public void for_uninitialized_fields_should_throw_exception(){
        Bmi bmiCounter = new BmiForKgM();
        bmiCounter.calculateBmi();
    }

    @Test
    public void for_valid_mass_and_height_should_return_correct_values(){
        Bmi bmiCounter = new BmiForKgM(75, 1.8);
        double result = bmiCounter.calculateBmi();
        assertEquals(result, 23.148, 0.01);

        bmiCounter.setMass(50);
        bmiCounter.setHeigth(1.6);
        result = bmiCounter.calculateBmi();
        assertEquals(result, 19.531, 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_zero_mass_and_height_should_throw_exception(){
        Bmi bmiCounter = new BmiForKgM(0, 0);
        bmiCounter.calculateBmi();
    }
}