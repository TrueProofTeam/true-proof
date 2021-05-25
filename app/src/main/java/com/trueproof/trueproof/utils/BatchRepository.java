package com.trueproof.trueproof.utils;

import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;

public class BatchRepository {
    String TAG = "BatchRepo";
    public void saveBatch (Batch batch){
        Amplify.API.mutate(ModelMutation.create(batch),
                response -> {
                    Log.i(TAG, "onSuccess: added");
                }, response -> {
                    Log.i(TAG, "onFail: miss");
                });
    }
}
