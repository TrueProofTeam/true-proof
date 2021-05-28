package com.trueproof.trueproof.viewmodels;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.amplifyframework.datastore.generated.model.Status;
import com.trueproof.trueproof.utils.AWSDateTime;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.JsonConverter;
import com.trueproof.trueproof.utils.MeasurementRepository;
import com.trueproof.trueproof.utils.UserSettings;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
@RequiresApi(api = Build.VERSION_CODES.O)
public class BatchDetailViewModel extends ViewModel {
    private static final String TAG = "BatchDetailViewModel";
    private final MutableLiveData<Batch> batchLiveData;
    private final MutableLiveData<List<Measurement>> measurementsLiveData;
    private final MutableLiveData<Boolean> updatedLiveData;
    JsonConverter jsonConverter;
    BatchRepository batchRepository;
    MeasurementRepository measurementRepository;
    UserSettings userSettings;

    @Inject
    BatchDetailViewModel(BatchRepository batchRepository,
                         MeasurementRepository measurementRepository,
                         UserSettings userSettings,
                         JsonConverter jsonConverter) {
        this.batchRepository = batchRepository;
        this.measurementRepository = measurementRepository;
        this.userSettings = userSettings;
        this.jsonConverter = jsonConverter;

        this.batchLiveData = new MutableLiveData<>();
        this.measurementsLiveData = new MutableLiveData<>();
        this.updatedLiveData = new MutableLiveData<>();
    }

    public LiveData<Batch> getBatch() {
        return batchLiveData;
    }

    public LiveData<List<Measurement>> getMeasurements() {
        return measurementsLiveData;
    }

    public void setBatchFromJson(String json) {
        Log.d(TAG, "setBatchFromJson: running");
        Batch batchFromJson = jsonConverter.batchFromJson(json);
        updateLiveData(batchFromJson);
    }

    public void updateBatch(Batch batch) {
        Batch updateBatch = batchLiveData.getValue().getStatus().equals(Status.ACTIVE) &&
                batch.getStatus().equals(Status.COMPLETE) ?
                batch.copyOfBuilder()
                        .completedAt(AWSDateTime.of(OffsetDateTime.now()))
                        .build() :
                batch;
        batchRepository.updateBatch(updateBatch,
                r -> {
                    Log.i(TAG, "updateBatch: batch updated succesfully");
                    this.batchLiveData.postValue(updateBatch);
                    updatedLiveData.postValue(true);
                },
                e -> {
                    updatedLiveData.postValue(false);
                    Log.e(TAG, "updateBatch: ApiException on updateBatch", e);
                });
    }

    public void update() {
        if (batchLiveData.getValue() != null) {
            batchRepository.getBatch(batchLiveData.getValue().getId(),
                    this::updateLiveData,
                    e -> {
                        Log.e(TAG, "update: ApiException on getBatch", e);
                    });
        } else {
            Log.d(TAG, "Called .update() but batch has not been populated yet.");
        }
    }

    private void updateLiveData(Batch batch) {
        ArrayList<Measurement> measurements = new ArrayList<>(batch.getMeasurements());
        measurements.sort(AWSDateTime.measurementByDate);
        measurementsLiveData.postValue(measurements);
        batchLiveData.postValue(batch);
    }

    public LiveData<Boolean> getUpdatedLiveData() {
        return updatedLiveData;
    }
}
