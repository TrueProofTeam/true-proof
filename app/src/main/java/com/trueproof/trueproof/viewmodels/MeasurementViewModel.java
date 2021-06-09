package com.trueproof.trueproof.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.trueproof.trueproof.logic.Proofing;
import com.trueproof.trueproof.logic.UnitConversions;
import com.trueproof.trueproof.utils.MeasurementRepository;

abstract class MeasurementViewModel extends ViewModel {
    private TemperatureUnit temperatureUnit = TemperatureUnit.FAHRENHEIT;
    private Double temperature;
    private Double temperatureCorrection;
    private Double hydrometer;
    private Double hydrometerCorrection;
    private MutableLiveData<Double> trueProof = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateEditTextLiveData = new MutableLiveData<>();

    MeasurementRepository measurementRepository;
    Proofing proofing;

    public LiveData<Boolean> getUpdateEditText() {
        return updateEditTextLiveData;
    }

    public TemperatureUnit getTemperatureUnit() {
        return temperatureUnit;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getTemperatureCorrection() {
        return temperatureCorrection;
    }

    public Double getHydrometer() {
        return hydrometer;
    }

    public Double getHydrometerCorrection() {
        return hydrometerCorrection;
    }

    public LiveData<Double> getTrueProof() {
        return trueProof;
    }

    public void setTemperatureUnit(TemperatureUnit temperatureUnit) {
        if (this.temperatureUnit == null) {
            this.temperatureUnit = temperatureUnit;
            updateEditTextLiveData.postValue(true);
            calculate();
        }
        if (!this.temperatureUnit.equals(temperatureUnit)) {
            convertTemperatureValues(temperatureUnit);
            this.temperatureUnit = temperatureUnit;
            updateEditTextLiveData.postValue(true);
            calculate();
        }
    }

    public void setTemperature(String temperature) {
        try {
            double num = Double.parseDouble(temperature);
            this.temperature = num;
        } catch (NumberFormatException e) {
            this.temperature = null;
        }
    }

    public void setTemperatureCorrection(String temperatureCorrection) {
        try {
            this.temperatureCorrection = Double.parseDouble(temperatureCorrection);
        } catch (NumberFormatException e) {
            this.temperatureCorrection = null;
        }
        calculate();
    }

    public void setHydrometer(String hydrometer) {
        try {
            this.hydrometer = Double.parseDouble(hydrometer);
        } catch (NumberFormatException e) {
            this.hydrometer = null;
        }
        calculate();
    }

    public void setHydrometerCorrection(String hydrometerCorrection) {
        try {
            this.hydrometerCorrection = Double.parseDouble(hydrometerCorrection);
        } catch (NumberFormatException e) {
            this.hydrometerCorrection = null;
        }
        calculate();
        calculate();
    }

    private void convertTemperatureValues(TemperatureUnit unit) {
        if (unit.equals(TemperatureUnit.FAHRENHEIT)) {
            if (temperature != null)
                temperature = UnitConversions.celsiusToFahrenheit(temperature);
            if (temperatureCorrection != null)
                temperatureCorrection = UnitConversions.celsiusCorrectionToFahrenheit(temperatureCorrection);
        } else {
            if (temperature != null)
                temperature = UnitConversions.fahrenheitToCelsius(temperature);
            if (temperatureCorrection != null)
                temperatureCorrection = UnitConversions.fahrenheitCorrectionToCelsius(temperatureCorrection);
        }
    }

    private void calculate() {
        if (temperature != null && hydrometer != null) {
            double temp = 0.0;
            double tempCorr = 0.0;
            if (temperatureUnit == TemperatureUnit.FAHRENHEIT) {
                temp = temperature;
                tempCorr = temperatureCorrection;
            } else {
                temp = UnitConversions.celsiusToFahrenheit(temperature);
                tempCorr = temperatureCorrection != null ?
                        UnitConversions.celsiusCorrectionToFahrenheit(temperatureCorrection) :
                        0.0;
            }
            double hydroCorr = hydrometerCorrection == null
                    ? 0.0 : hydrometerCorrection;

            try {
                double proof = proofing.proofWithCorrection(
                        temp,
                        hydrometer,
                        tempCorr,
                        hydroCorr);
                trueProof.postValue(proof);
            } catch (IllegalArgumentException e) {
                trueProof.postValue(null);
            }
        }
    }
}
