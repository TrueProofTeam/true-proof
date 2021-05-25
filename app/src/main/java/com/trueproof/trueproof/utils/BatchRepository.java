package com.trueproof.trueproof.utils;

import android.util.Log;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Status;

import java.util.ArrayList;
import java.util.List;

public class BatchRepository {
    String TAG = "BatchRepo";

    public void saveBatch(Batch batch) {
        Amplify.API.mutate(ModelMutation.create(batch),
                response -> {
                    Log.i(TAG, "onSuccess: added");
                }, response -> {
                    Log.i(TAG, "onFail: miss");
                });
    }

    public void getBatchesByDistillery(Distillery distillery, Consumer<List<Batch>> onSuccess, Consumer<ApiException> onFail) {
        List<Batch> output = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Batch.class, Batch.DISTILLERY.contains(distillery.getId())),
                response -> {
                    for (Batch batch : response.getData()) {
                        output.add(batch);
                    }
                    onSuccess.accept(output);
                },

                onFail
        );

    }

    public void getBatch(String id, Consumer<Batch> onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.query(
                ModelQuery.get(Batch.class, id),
                r -> {
                    onSuccess.accept(r.getData());
                },
                onFail
        );
    }

    public void updateBatch(Batch batch, Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(ModelMutation.update(batch),
                onSuccess,
                onFail);
    }

    public void getActiveBatchesByDistillery(Distillery distillery, Consumer<List<Batch>> onSuccess, Consumer<ApiException> onFail) {
        List<Batch> output = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Batch.class, Batch.DISTILLERY.contains(distillery.getId())),
                response -> {
                    for (Batch batch : response.getData()) {
                        if (batch.getStatus().equals(Status.ACTIVE)) output.add(batch);

                    }
                    onSuccess.accept(output);
                },

                onFail
        );
    }

    public void getCompleteBatchesByDistillery(Distillery distillery, Consumer<List<Batch>> onSuccess, Consumer<ApiException> onFail) {
        List<Batch> output = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Batch.class, Batch.DISTILLERY.contains(distillery.getId())),
                response -> {
                    for (Batch batch : response.getData()) {
                        if (batch.getStatus().equals(Status.COMPLETE)) output.add(batch);

                    }
                    onSuccess.accept(output);
                },

                onFail
        );
    }
    public void deleteBatch (Batch batch, Consumer onSuccess, Consumer<ApiException>onFail){

            Amplify.API.mutate(ModelMutation.delete(batch), onSuccess, onFail);

    }

}
