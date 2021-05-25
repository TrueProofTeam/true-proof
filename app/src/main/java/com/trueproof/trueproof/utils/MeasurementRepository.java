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

    public void saveMeasurement(Measurement measurement,Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(ModelMutation.create(measurement),
                onSuccess, onFail);
    }


    public void getMeasurementsByBatch(Batch batch, Consumer<List<Measurement>> onSuccess, Consumer<ApiException> onFail) {
        List<Measurement> output = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Measurement.class, Measurement.BATCH.contains(batch.getId())),
                response -> {
                    for (Measurement measurement : response.getData()) {
                        output.add(measurement);
                    }
                    onSuccess.accept(output);
                },

                onFail
        );

    }

    public void getLastMeasurementByBatch(Batch batch, Consumer<Measurement> onSuccess, Consumer<ApiException> onFail) {
        List<Measurement> output = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Measurement.class, Measurement.BATCH.contains(batch.getId())),
                response -> {
                    for (Measurement measurement : response.getData()) {
                        output.add(measurement);
                    }
                    onSuccess.accept(output.get(0));
                },
                onFail
        );

    }

    public void getLastMeasurementsByBatch(Batch batch, int amount, Consumer<Measurement> onSuccess, Consumer<ApiException> onFail) {
        List<Measurement> output = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Measurement.class, Measurement.BATCH.contains(batch.getId())),
                response -> {
                    int count = 0;
                    while (count < amount) {


                        for (Measurement measurement : response.getData()) {
                            output.add(measurement);
                            count++;
                        }

                    }
                    onSuccess.accept(output.get(0));
                },
                onFail
        );
    }

    public void updateMeasurement(Measurement measurement, Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(
                ModelMutation.update(measurement),
                onSuccess,
                onFail
        );
    }

    public void deleteMeasurement(Measurement measurement, Consumer onSuccess, Consumer<ApiException> onFail) {

        Amplify.API.mutate(ModelMutation.delete(measurement), onSuccess, onFail);

    }
}
