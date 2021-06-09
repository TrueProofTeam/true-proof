package com.trueproof.core.logic;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ProofingTest {
    static final double DELTA = 0.005;
    Proofing proofing;

    @Before
    public void initialize() throws FileNotFoundException {
        String filePath = "src/main/resources/table1.csv";
        File file = new File(filePath);
        proofing = new Proofing(new FileInputStream(file));
    }

    @Test
    public void validLookups() {
        assertEquals(101.9, proofing.lookup(1, 76), DELTA);
        assertEquals(112.2, proofing.lookup(5, 90), DELTA);
        assertEquals(134.4, proofing.lookup(10, 116), DELTA);
        assertEquals(135.3, proofing.lookup(21, 121), DELTA);
        assertEquals(0.7, proofing.lookup(51, 0), DELTA);
        assertEquals(0.0, proofing.lookup(60, 0), DELTA);
        assertEquals(40.0, proofing.lookup(60, 40), DELTA);
        assertEquals(80.0, proofing.lookup(60, 80), DELTA);
        assertEquals(199.5, proofing.lookup(87, 204), DELTA);
        assertEquals(199.6, proofing.lookup(100, 206), DELTA);
    }

    @Test
    public void invalidLookups() {
        int[][] outOfRangePairs = {
                {101, 206},
                {101, 207},
                {0, 120},
                {-1, 120},
                {1, 75},
                {11, 190},
                {16, 191},
                {59, 200}
        };

        for(int[] pair : outOfRangePairs) {
            int temperature = pair[0];
            int hydrometer = pair[1];
            assertThrows(IllegalArgumentException.class,
                    () -> proofing.lookup(temperature, hydrometer));
        }
    }

    @Test
    public void simpleProofTest() {
        assertEquals(80, proofing.proof(60, 80), DELTA);
    }

    @Test
    public void proofWithCorrectionTest() {
        double ttbSample = proofing.proofWithCorrection(72.1, 192.85, 0.05, -0.03);
        assertEquals(189.972, ttbSample, DELTA);
    }

}
