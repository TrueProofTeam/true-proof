package com.trueproof.trueproof.logic;

public class UnitConversions {
    static double fahrenheitToCelsius(double degreesFahrenheit) {
        return (degreesFahrenheit - 32.0) / 1.8;
    }

    static double celsiusToFahrenheit(double degreesCelsius) {
        return degreesCelsius * 1.8 + 32.0;
    }
}
