package com.trueproof.trueproof.logic;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Proofing {

    List<List<String>> table;

    public Proofing(InputStream inputStream) {
        table = new ArrayList<>();
        Scanner scanner;

        try {
            scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                String line = scanner.next();
                String[] values = line.split(",");
                table.add(Arrays.asList(values));
            }
            scanner.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Takes in Fahrenheit, C converted to F before being passed as in
    public double proof(double temperature, double proof, double proofCorrection, double tempCorrection) {

//        System.out.println("temperature = " + temperature);
//        System.out.println("proof = " + proof);
//        System.out.println("proofCorrection = " + proofCorrection);
//        System.out.println("tempCorrection = " + tempCorrection);

            /* TODO
            [✓] 5/24 bring in CSV through getResources() instead of file path
            [] accept temperature in both F and C and convert where appropriate
                F -> C equation: (F - 32) * (5/9) = C
            [] convert F to C in activity/intent, Proof method always takes temperature in C
            [✓] 5/24 handle simple proofing (no interpolation) with fractional temp/proof values. round up/down?
            [✓] 5/25 handle exceptions if args return invalid value from table
             */

        if (temperature <= 1){
            temperature = 1;
        }
        if (temperature >= 100){
            temperature = 99;
        }
//        if (proof <= 1){
//            proof = 1;
//        }
        if (proof > 206){
            proof = 206;
        }

        // simple proofing for quick calculation when correction factors not selected or set to 0, no interpolation just intersection of TTB table
        if (proofCorrection == 0.0 && tempCorrection == 0.0) {
            double roundTemp = Math.round(temperature);
            double roundProof = Math.round(proof);

//            if (roundTemp < 1){
//                roundTemp = 1;
//            }
//            if (roundProof > 100){
//                roundTemp = 100
//            }

//            System.out.println("temperature = " + temperature);
//            System.out.println("roundTemp = " + roundTemp);
//            System.out.println("proof = " + proof);
//            System.out.println("roundProof = " + roundProof);

            double chartProof = Double.parseDouble(table.get((int) roundTemp - 1).get((int) roundProof));
//            System.out.println("chartProof = " + (chartProof / 10));
//
            Log.v("Chart Proof: ", String.valueOf(chartProof / 10));

            return chartProof / 10;
        }

        // Interpolation begins

        // Adjust measured values by instrument correction factors
        double correctedProof = proof + proofCorrection;
        double correctedTemp = temperature + tempCorrection;

        // C->F conversion,
//        double celsiusToFahrenheitConversion = ((correctedTemp * 1.8) + 32);

        // find proof at standardized temp (68F) when measured proof rounded high/low
        double highProof = Double.parseDouble(table.get(68 - 1).get((int) Math.ceil(correctedProof)));
        double lowProof = Double.parseDouble(table.get(68 - 1).get((int) Math.floor(correctedProof)));

        double proofDifference = highProof - lowProof;

        // find proof at standardized proof (80) when measured temp rounded high/low
        System.out.println("correctedTemp = " + (correctedTemp-1));
        double highTemp = Double.parseDouble(table.get((int) Math.ceil(correctedTemp - 1)).get(80));
        double lowTemp = Double.parseDouble(table.get((int) Math.floor(correctedTemp - 1)).get(80));

        double tempDifference = highTemp - lowTemp;

        // isolate fractional portion of measured temp/proof
        double proofFractional = correctedProof - Math.floor(proof);
        double tempFractional = correctedTemp - Math.floor(temperature);

        //interpolate proof and temp
        double proofInterpolation = proofFractional * proofDifference;
        double tempInterpolation = tempFractional * tempDifference;

        // interpolate true proof
        double interpolatedProof = (double) Math.round((lowProof + proofInterpolation + (tempInterpolation)));
        //System.out.println("interpolatedProof = " + interpolatedProof);

        // divide big dumb Integer by 10 to find it's actual proof value;
        //Log.e("Interpolated Proof: ", String.valueOf(interpolatedProof / 10));
        return (interpolatedProof / 10);
    }
}
