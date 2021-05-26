package com.trueproof.trueproof.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.UserSettings;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BatchListViewModel extends ViewModel {
    private final MutableLiveData<List<Batch>> activeBatchList;
    private final MutableLiveData<List<Batch>> batchList;
    private final MutableLiveData<Distillery> distillery;
    private final BatchRepository batchRepository;
    private final UserSettings userSettings;

    @Inject
    BatchListViewModel(BatchRepository batchRepository, UserSettings userSettings) {
        this.batchRepository = batchRepository;
        this.userSettings = userSettings;

        this.distillery = new MutableLiveData<>();
        userSettings.getDistillery(
                d -> this.distillery.postValue(d),
                e -> {
                    // TODO
                    Log.e("BatchListViewModel", "BatchListViewModel: AHHH");
                }
                );
        this.activeBatchList = new MutableLiveData<>(new ArrayList<>());
        this.batchList = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Batch>> getActiveBatchList() {
        return activeBatchList;
    }

    public LiveData<List<Batch>> getBatchList() {
        return batchList;
    }

    public LiveData<Distillery> getDistillery() {
        return distillery;
    }

    private void updateBatchLists(Distillery distillery) {
        batchRepository.getCompleteBatchesByDistillery(distillery,
                batches -> { batchList.postValue(batches); },
                r -> {
                    Log.e("BatchListViewModel", "getCompleteBatchesByDistillery fail" + r);
                });
        batchRepository.getActiveBatchesByDistillery(distillery,
                activeBatches -> { activeBatchList.postValue(activeBatches); },
                r -> {
                    Log.e("BatchListViewModel", "getActiveBatchesByDistillery fail" + r);
                });
    }
}
