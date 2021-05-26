package com.trueproof.trueproof.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.JsonConverter;
import com.trueproof.trueproof.utils.MeasurementRepository;
import com.trueproof.trueproof.utils.UserSettings;

import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BatchDetailViewModel extends ViewModel {
    private static final String TAG = "BatchDetailViewModel";
    private final MutableLiveData<Batch> batch;
    private final MutableLiveData<List<Measurement>> measurements;

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

        this.batch = new MutableLiveData<>();
        this.measurements = new MutableLiveData<>();
    }

    public LiveData<Batch> getBatch() {
        return batch;
    }

    public void setBatchFromJson(String json) {
        Batch batchFromJson = jsonConverter.batchFromJson(json);
        batch.setValue(batchFromJson);
        measurements.postValue(batchFromJson.getMeasurements());
    }

    public void updateBatch(Batch batch) {
        this.batch.postValue(batch);
        batchRepository.updateBatch(batch,
                r -> {
                    // TODO
                },
                r -> {
                    // TODO
                });
    }

    public void update() {
        if (batch.getValue() != null) {
            batchRepository.getBatch(batch.getValue().getId(),
                    updatedBatch -> {
                        batch.postValue(updatedBatch);
                        measurements.postValue(updatedBatch.getMeasurements());
                    },
                    e -> {
                        Log.e(TAG, "update: ApiException on getBatch", e);
                    });
        } else {
            throw new NoSuchElementException("ViewModel has not been populated with an initial batch. "
                    + "Call viewModel.setBatchFromJson before updating from database");
        }
    }
}
