package com.trueproof.trueproof.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Proofing {
    short[][] table;

    public Proofing(InputStream inputStream) {
        table = new short[100][207];
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            for (int i = 0; i < 100; i++) {
                String line = bufferedReader.readLine();
                String[] values = line.split(",");
                for (int j = 0; j < 207; j++) {
                    table[i][j] = Short.parseShort(values[j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes a temperature value in Fahrenheit units and a Hydrometer reading in Proof units and
     * calculates the true proof at 60 degrees Fahrenheit using the TTB tables and bilinear
     * interpolation.
     *
     * @param temperature A temperature value in degrees Fahrenheit
     * @param hydrometer A hydrometer reading in degrees proof
     * @return The true proof at 60 degrees Fahrenheit calculated using the TTB tables and bilinear
     * interpolation
     * @throws IllegalArgumentException If the temperature and hydrometer reading doesn't correspond
     *                                  to a measurable true proof value using the TTB tables
     */
    public double proof(double temperature, double hydrometer) throws IllegalArgumentException {
        int t0 = lowerInt(temperature);
        int t1 = higherInt(temperature);
        int h0 = lowerInt(hydrometer);
        int h1 = higherInt(hydrometer);

        double x0 = t0;
        double x1 = t1;
        double y0 = h0;
        double y1 = h1;

        double z00 = lookup(t0, h0);
        double z01 = lookup(t0, h1);
        double z10 = lookup(t1, h0);
        double z11 = lookup(t1, h1);

        return Interpolation.bilinear(x0, x1, y0, y1, z00, z01, z10, z11, temperature, hydrometer);
        //return Interpolation.simple2D(x0, x1, y0, y1, z00, z01, z10, temperature, hydrometer);
    }

    public double lookup(int temperature, int hydrometer) {
        if (temperature < 1 || temperature > 100)
            throw new IllegalArgumentException("Temperature out of range");
        if (hydrometer < 0 || hydrometer > 206)
            throw new IllegalArgumentException("Temperature out of range");
        short value = table[temperature - 1][hydrometer];
        if (value < 0)
            throw new IllegalArgumentException("Temperature and hydrometer values out of range");

        return ((double) value) / 10.0;
    }

    private int lowerInt(double value) {
        return (int) Math.floor(value);
    }

    private int higherInt(double value) {
        return lowerInt(value) + 1;
    }

    public double proofWithCorrection(
            double temperature,
            double hydrometer,
            double temperatureCorrection,
            double hydrometerCorrection) {
        return proof(temperature + temperatureCorrection, hydrometer + hydrometerCorrection);
    }
}
