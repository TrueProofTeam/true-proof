package com.trueproof.trueproof.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.datastore.generated.model.Batch;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.JsonConverter;
import com.trueproof.trueproof.utils.UserSettings;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BatchDetailViewModel extends ViewModel {
    private final MutableLiveData<Batch> batch;

    JsonConverter jsonConverter;
    BatchRepository batchRepository;
    UserSettings userSettings;

    @Inject
    BatchDetailViewModel(BatchRepository batchRepository,
                         UserSettings userSettings,
                         JsonConverter jsonConverter) {
        this.batchRepository = batchRepository;
        this.userSettings = userSettings;
        this.jsonConverter = jsonConverter;

        this.batch = new MutableLiveData<>();
    }

    public void setBatch(String json) {
        batch.setValue(jsonConverter.batchFromJson(json));
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
}
