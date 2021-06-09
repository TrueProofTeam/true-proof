package com.trueproof.trueproof.logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnitConversionsTest {
    final static double DELTA = 0.001;

    @Test
    public void fahrenheitToCelsius() {
        assertEquals(0, UnitConversions.fahrenheitToCelsius(32), DELTA);
        assertEquals(26.6667, UnitConversions.fahrenheitToCelsius(80), DELTA);
    }

    @Test
    public void celsiusToFahrenheit() {
        assertEquals(32, UnitConversions.celsiusToFahrenheit(0), DELTA);
        assertEquals(113, UnitConversions.celsiusToFahrenheit(45), DELTA);
    }
}