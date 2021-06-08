package com.trueproof.trueproof.logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class InterpolationTest {
    static final double DELTA = 0.001;

    @Test
    public void linear() {
        assertEquals(2.4, Interpolation.linear(0.0, 0.0, 10.0, 5.0, 4.8), DELTA);
        assertEquals(4, Interpolation.linear(3, 0.0, 6.0, 6.0, 5), DELTA);
    }

    @Test
    public void simple2D() {
        assertEquals(10,
                Interpolation.simple2D(0, 10, 0, 10, 0, 10, 10, 5, 5),
                DELTA);
    }

    @Test
    public void bilinear() {
        assertEquals(10,
                Interpolation.bilinear(0, 10, 0, 10, 0, 10, 10, 20, 5, 5),
                DELTA);
    }
}