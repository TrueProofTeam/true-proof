package com.trueproof.trueproof;

import com.trueproof.trueproof.logic.Proofing;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Testing {

    Proofing proofing;

    @Before
    public void something() throws FileNotFoundException {
        String filePath = "C:\\Users\\banan\\Documents\\CodeFellows\\401\\true-proof\\app\\src\\main\\res\\raw\\table1.csv";
        File file = new File(filePath);
        proofing = new Proofing(new FileInputStream(file));
    }

    @Test
    public void simpleProofTest(){
        double pgTest = proofing.proof(60, 80, 0, 0);
        System.out.println("pgTest = " + pgTest);

        double fractionalTest = proofing.proof(45.2, 80.9, 0, 0);
        System.out.println("fractionalTest = " + fractionalTest);

        double invalidHighValues = proofing.proof(96, 206, 0, 0);
        System.out.println("invalidHighValues = |" + invalidHighValues + "| should be null, proof at these measurements is impossible");

        double offChartValues = proofing.proof(1, 50, 0 ,0);
        System.out.println("offChartValues = |" + offChartValues + "| should be null, proof at these measurements is impossible");
    }

    @Test
    public void interpolatedProofTest(){
        double ttbSample = proofing.proof(20.1, 80.35, -0.03, 0.1);
        System.out.println("ttbSample = |" + ttbSample + "| should be 76.6");

    }
}
