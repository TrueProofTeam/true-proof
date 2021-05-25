package com.trueproof.trueproof.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.amplifyframework.datastore.generated.model.Status;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.MeasurementRepository;

import org.jetbrains.annotations.NotNull;

public class DistillerySettingsActivity extends AppCompatActivity {

    static String TAG = "t.userSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distillery_settings);
        Distillery distillery = Distillery.builder().name("Old Thyme Whiskey").dspId("ASDF10255").build();

        Measurement measurement = Measurement.builder().trueProof(60.0).temperature(30.0).hydrometer(10.0).temperatureCorrection(0.1).hydrometerCorrection(0.1).build();
        MeasurementRepository measurementRepository = new MeasurementRepository();
//        Consumer consumer = new Consumer() {
//            @Override
//            public void accept(@NonNull @NotNull Object value) {
//                Log.i(TAG, "accept: WORKS");
//            }
//        };
//        measurementRepository.saveMeasurement(measurement, consumer, new Consumer<ApiException>() {
//            @Override
//            public void accept(@NonNull @NotNull ApiException value) {
//                Log.i(TAG, "accept: FAILED");
//            }
//    });

        Amplify.API.mutate(ModelMutation.create(measurement),
                response ->{
                    Log.i(TAG, "oncreate success");
                },
                response -> {
                    Log.i(TAG, "onCreate: fail");

                });



    }
}