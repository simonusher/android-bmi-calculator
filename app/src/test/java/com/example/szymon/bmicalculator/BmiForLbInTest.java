package com.example.szymon.bmicalculator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Szymon on 11.03.2018.
 */

public class BmiForLbInTest {
    @Test(expected = IllegalArgumentException.class)
    public void for_uninitialized_fields_should_throw_exception(){
        Bmi bmiCounter = new BmiForLbIn();
        bmiCounter.calculateBmi();
    }

    @Test
    public void for_valid_mass_and_height_should_return_correct_values(){
        Bmi bmiCounter = new BmiForLbIn(165, 70.86);
        double result = bmiCounter.calculateBmi();
        assertEquals(result, 23.101, 0.01);

        bmiCounter.setMass(110);
        bmiCounter.setHeigth(63);
        result = bmiCounter.calculateBmi();
        assertEquals(result, 19.483, 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_zero_mass_and_height_should_throw_exception(){
        Bmi bmiCounter = new BmiForLbIn(0, 0);
        bmiCounter.calculateBmi();
    }
}
