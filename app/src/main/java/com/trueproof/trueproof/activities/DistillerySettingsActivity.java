package com.trueproof.trueproof.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.amplifyframework.datastore.generated.model.Status;
import com.trueproof.trueproof.R;

public class DistillerySettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distillery_settings);
        Distillery distillery = Distillery.builder().name("Old Thyme Whiskey").dspId("ASDF10255").build();
//        Amplify.API.mutate(ModelMutation.create(distillery),
//                response -> {
////                        Log.i(TAG, "onCreate: added");
//                }, response -> {
////                        Log.i(TAG, "onCreate: miss");
//                });
//        Batch batch = Batch.builder().batchIdentifier("test").batchNumber(40).status(Status.ACTIVE).distillery(distillery).build();
//        Amplify.API.mutate(ModelMutation.create(batch),
//                response -> {
////                        Log.i(TAG, "onCreate: added");
//                }, response -> {
////                        Log.i(TAG, "onCreate: miss");
//                });
//        Measurement measurement = Measurement.builder().trueProof(60.0).temperature(91.0).hydrometer(60.0).temperatureCorrection(0.0).hydrometerCorrection(5.0).batch(batch).build();
//        Amplify.API.mutate(ModelMutation.create(measurement),
//                response -> {
////                        Log.i(TAG, "onCreate: added");
//                }, response -> {
////                        Log.i(TAG, "onCreate: miss");
//                });
    }
}