package com.trueproof.trueproof.utils;

import android.util.Log;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MeasurementRepository {
    String TAG = "MeasurementRepo";
    public void saveMeasurement (Measurement measurement){
        Amplify.API.mutate(ModelMutation.create(measurement),
                response -> {
                    Log.i(TAG, "onSuccess: added");
                }, response -> {
                    Log.i(TAG, "onFail: miss");
                });
    }
//    when calling this pass in own callback
    public void getMeasurementsByBatch(Batch batch, Consumer<List<Measurement>> onSuccess, Consumer<ApiException> onFail){
        List <Measurement> output = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Measurement.class, Measurement.BATCH.contains(batch.getId())),
                response -> {
                    for (Measurement measurement:  response.getData()) {
                        output.add(measurement);
                    }
                    onSuccess.accept(output);
                },
//                try .eq instead of contains
                onFail
        );
//        return output;
    }
}
