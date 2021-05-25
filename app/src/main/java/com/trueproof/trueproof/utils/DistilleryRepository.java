package com.trueproof.trueproof.utils;

import android.util.Log;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;

import java.util.ArrayList;
import java.util.List;

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
    public void getDistillery(String id, Consumer<Distillery> onSuccess, Consumer<ApiException> onFail){
        List <Batch> output = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Batch.class, Batch.DISTILLERY.contains(id)),
                response -> {
                    for (Batch batch:  response.getData()) {
                        output.add(batch);
                    }
//                    onSuccess.accept(output);
                },

                onFail
        );
//        return output;
    }
    public void updateDistillery (Distillery distillery, Consumer<Distillery> onSuccess, Consumer<ApiException> onFail){

    }
    public void getDistilleryByUser (String email, Consumer<Distillery> onSuccess, Consumer<ApiException> onFail){

    }

}
