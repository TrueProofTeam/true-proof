package com.trueproof.trueproof.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.trueproof.core.logic.Proofing;
import com.trueproof.core.logic.UnitConversions;
import com.trueproof.trueproof.utils.MeasurementRepository;

public abstract class MeasurementViewModel extends ViewModel {
    MeasurementRepository measurementRepository;
    Proofing proofing;
    private TemperatureUnit temperatureUnit = TemperatureUnit.FAHRENHEIT;
    private Double temperature;
    private Double temperatureCorrection;
    private Double hydrometer;
    private Double hydrometerCorrection;
    private final MutableLiveData<Double> trueProof = new MutableLiveData<>();
    private final MutableLiveData<Boolean> updateEditTextLiveData = new MutableLiveData<>();

    public LiveData<Boolean> getUpdateEditText() {
        return updateEditTextLiveData;
    }

    public TemperatureUnit getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(TemperatureUnit temperatureUnit) {
        if (this.temperatureUnit == null) {
            this.temperatureUnit = temperatureUnit;
            updateEditTextLiveData.postValue(true);
            updateTrueProof();
        }
        if (!this.temperatureUnit.equals(temperatureUnit)) {
            convertTemperatureValues(temperatureUnit);
            this.temperatureUnit = temperatureUnit;
            updateEditTextLiveData.postValue(true);
            updateTrueProof();
        }
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        try {
            double num = Double.parseDouble(temperature);
            this.temperature = num;
        } catch (NumberFormatException e) {
            this.temperature = null;
        }
    }

    public Double getTemperatureCorrection() {
        return temperatureCorrection;
    }

    public void setTemperatureCorrection(String temperatureCorrection) {
        try {
            this.temperatureCorrection = Double.parseDouble(temperatureCorrection);
        } catch (NumberFormatException e) {
            this.temperatureCorrection = null;
        }
        updateTrueProof();
    }

    public Double getHydrometer() {
        return hydrometer;
    }

    public void setHydrometer(String hydrometer) {
        try {
            this.hydrometer = Double.parseDouble(hydrometer);
        } catch (NumberFormatException e) {
            this.hydrometer = null;
        }
        updateTrueProof();
    }

    public Double getHydrometerCorrection() {
        return hydrometerCorrection;
    }

    public void setHydrometerCorrection(String hydrometerCorrection) {
        try {
            this.hydrometerCorrection = Double.parseDouble(hydrometerCorrection);
        } catch (NumberFormatException e) {
            this.hydrometerCorrection = null;
        }
        updateTrueProof();
        updateTrueProof();
    }

    public LiveData<Double> getTrueProof() {
        return trueProof;
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

    private void updateTrueProof() {
        if (temperature != null && hydrometer != null) {
            trueProof.postValue(calculateTrueProof());
        }
    }

    Double calculateTrueProof() {
        if (temperature == null || hydrometer == null) return null;
        double temp = 0.0;
        double tempCorr = 0.0;
        if (temperatureUnit == TemperatureUnit.FAHRENHEIT) {
            temp = temperature;
            tempCorr = temperatureCorrection != null ?
                    temperatureCorrection : 0.0;
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
            return proof;
        } catch (IllegalArgumentException ignored) {
        }
        return null;
    }
}
