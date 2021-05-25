package com.trueproof.trueproof.utils;

import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Distillery;

public class DistilleryRepository {
    String TAG = "DistilleryRepo";
    public void saveDistillery (Distillery distillery){
        Amplify.API.mutate(ModelMutation.create(distillery),
                response -> {
                        Log.i(TAG, "onSuccess: added");
                }, response -> {
                        Log.i(TAG, "onFail: miss");
                });
    }
}
