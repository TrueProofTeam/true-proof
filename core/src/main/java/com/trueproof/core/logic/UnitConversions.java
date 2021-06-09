package com.trueproof.core.logic;

public class UnitConversions {
    public static double fahrenheitToCelsius(double degreesFahrenheit) {
        return (degreesFahrenheit - 32.0) / 1.8;
    }

    public static double celsiusToFahrenheit(double degreesCelsius) {
        return degreesCelsius * 1.8 + 32.0;
    }

    public static double fahrenheitCorrectionToCelsius(double degreesFahrenheit) {
        return degreesFahrenheit / 1.8;
    }

    public static double celsiusCorrectionToFahrenheit(double degreesCelsius) {
        return degreesCelsius * 1.8;
    }
}
