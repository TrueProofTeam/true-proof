package com.trueproof.trueproof.viewmodels;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.amplifyframework.datastore.generated.model.User;
import com.trueproof.core.logic.Proofing;
import com.trueproof.core.logic.UnitConversions;
import com.trueproof.trueproof.utils.AWSDateTime;
import com.trueproof.trueproof.utils.JsonConverter;
import com.trueproof.trueproof.utils.MeasurementRepository;

import java.time.OffsetDateTime;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
@RequiresApi(api = Build.VERSION_CODES.O)
public class TakeMeasurementViewModel extends MeasurementViewModel {
    private static final String TAG = "TakeMeasurement/VM/";
    private final JsonConverter jsonConverter;
    private final MutableLiveData<Boolean> updatedLiveData = new MutableLiveData<>();
    private Batch batch;

    @Inject
    TakeMeasurementViewModel(
            MeasurementRepository measurementRepository,
            JsonConverter jsonConverter,
            Proofing proofing
    ) {
        this.proofing = proofing;
        this.measurementRepository = measurementRepository;
        this.jsonConverter = jsonConverter;
    }

    public Batch getBatch() {
        return batch;
    }

    public LiveData<Boolean> getSavedLiveData() {
        return updatedLiveData;
    }

    public void setBatchFromJson(String batchJson) {
        this.batch = jsonConverter.batchFromJson(batchJson);
    }

    public void saveMeasurement() {
        double temp = 0.0;
        double tempCorr = 0.0;
        if (getTemperatureUnit() == TemperatureUnit.FAHRENHEIT) {
            temp = getTemperature();
            tempCorr = getTemperatureCorrection();
        } else {
            temp = UnitConversions.celsiusToFahrenheit(getTemperature());
            tempCorr = getTemperatureCorrection() != null ?
                    UnitConversions.celsiusCorrectionToFahrenheit(getTemperatureCorrection()) :
                    0.0;
        }
        double hydroCorr = getHydrometerCorrection() == null
                ? 0.0 : getHydrometerCorrection();
        Measurement newMeasurement = Measurement.builder()
                .trueProof(calculateTrueProof())
                .temperature(temp)
                .hydrometer(getHydrometer())
                .temperatureCorrection(tempCorr)
                .hydrometerCorrection(hydroCorr)
                .batch(Batch.justId(batch.getId()))
                .takenAt(AWSDateTime.of(OffsetDateTime.now()))
                .flag(false)
                .note("")
                .build();

        Log.i(TAG, "newMeasurement: " + newMeasurement);

        measurementRepository.saveMeasurement(newMeasurement,
                r -> {
                    Log.i(TAG, "saveMeasurement: success");
                    updatedLiveData.postValue(true);
                },
                f -> {
                    Log.i(TAG, "saveMeasurement: failure");
                    updatedLiveData.postValue(false);
                });
    }

    public void setUserSettings(User user) {
        setTemperatureCorrection(user.getDefaultTemperatureCorrection().toString());
        setHydrometerCorrection(user.getDefaultHydrometerCorrection().toString());
        setTemperatureUnit(user.getDefaultTemperatureUnit());
    }
}
