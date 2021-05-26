package com.trueproof.trueproof.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.datastore.generated.model.Batch;
import com.trueproof.trueproof.utils.BatchRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BatchListViewModel extends ViewModel {
    private MutableLiveData<List<Batch>> activeBatchList;
    private MutableLiveData<List<Batch>> batchList;
    private BatchRepository batchRepository;

    @Inject
    BatchListViewModel(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public LiveData<List<Batch>> getActiveBatchList() {
        return activeBatchList;
    }

    public LiveData<List<Batch>> getBatchList() {
        return batchList;
    }
}
