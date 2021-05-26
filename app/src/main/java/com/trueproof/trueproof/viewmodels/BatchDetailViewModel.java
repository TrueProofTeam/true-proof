package com.trueproof.trueproof.viewmodels;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.JsonConverter;
import com.trueproof.trueproof.utils.MeasurementRepository;
import com.trueproof.trueproof.utils.UserSettings;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
@RequiresApi(api = Build.VERSION_CODES.N)
public class BatchDetailViewModel extends ViewModel {
    private static final String TAG = "BatchDetailViewModel";
    private final MutableLiveData<Batch> batchLiveData;
    private final MutableLiveData<List<Measurement>> measurementsLiveData;
    private final Comparator<Measurement> byDate = new Comparator<Measurement>() {
        @Override
        public int compare(Measurement m1, Measurement m2) {
            return m1.getCreatedAt().compareTo(m2.getCreatedAt());
        }
    };
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
    }

    public LiveData<Batch> getBatch() {
        return batchLiveData;
    }

    public void setBatchFromJson(String json) {
        Batch batchFromJson = jsonConverter.batchFromJson(json);
        batchFromJson.getMeasurements().sort(byDate);
        batchLiveData.setValue(batchFromJson);
        measurementsLiveData.postValue(batchFromJson.getMeasurements());
    }

    public void updateBatch(Batch batch) {
        batch.getMeasurements().sort(byDate);
        this.batchLiveData.postValue(batch);
        batchRepository.updateBatch(batch,
                r -> {
                    Log.i(TAG, "updateBatch: batch updated succesfully");
                    this.batchLiveData.postValue(batch);
                },
                e -> {
                    Log.e(TAG, "updateBatch: ApiException on updateBatch", e);
                });
    }

    public void update() {
        if (batchLiveData.getValue() != null) {
            batchRepository.getBatch(batchLiveData.getValue().getId(),
                    updatedBatch -> {
                        updatedBatch.getMeasurements().sort(byDate);
                        batchLiveData.postValue(updatedBatch);
                        measurementsLiveData.postValue(updatedBatch.getMeasurements());
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
