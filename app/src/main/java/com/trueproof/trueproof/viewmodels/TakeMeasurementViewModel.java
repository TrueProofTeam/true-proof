package com.trueproof.trueproof.viewmodels;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.trueproof.trueproof.utils.AWSDateTime;
import com.trueproof.trueproof.utils.JsonConverter;
import com.trueproof.trueproof.utils.MeasurementRepository;

import java.time.OffsetDateTime;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
@RequiresApi(api = Build.VERSION_CODES.O)
public class TakeMeasurementViewModel extends ViewModel {
    private static final String TAG = "TakeMeasurement/VM/";
    private final MeasurementRepository measurementRepository;
    private final JsonConverter jsonConverter;
    private MutableLiveData<Boolean> updatedLiveData;
    private Batch batch;

    private Measurement measurement;
    private TemperatureUnit unit;


    @Inject
    TakeMeasurementViewModel(
            MeasurementRepository measurementRepository,
            JsonConverter jsonConverter
    ) {
        this.measurementRepository = measurementRepository;
        this.jsonConverter = jsonConverter;
        this.updatedLiveData = new MutableLiveData<>();
    }

    public Batch getBatch() {
        return batch;
    }

    public LiveData<Boolean> getUpdatedLiveData() {
        return updatedLiveData;
    }

    public void setBatchFromJson(String batchJson) {
        this.batch = jsonConverter.batchFromJson(batchJson);
    }

    public void saveMeasurement(Measurement measurement) {
        Measurement newMeasurement = measurement.copyOfBuilder()
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

}
