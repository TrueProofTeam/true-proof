package com.trueproof.trueproof.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.MeasurementRepository;

import org.jetbrains.annotations.NotNull;

public class TakeMeasurementActivity extends AppCompatActivity {

    static String TAG = "t.takeMeasurement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_measurement);

        Measurement measurement = Measurement.builder().trueProof(60.0).temperature(30.0).hydrometer(10.0).temperatureCorrection(0.1).hydrometerCorrection(0.1).build();
        MeasurementRepository measurementRepository = new MeasurementRepository();
        Consumer consumer = new Consumer() {
            @Override
            public void accept(@NonNull @NotNull Object value) {
                Log.i(TAG, "accept: WORKS");
            }
        };
        measurementRepository.saveMeasurement(measurement, consumer, new Consumer<ApiException>() {
            @Override
            public void accept(@NonNull @NotNull ApiException value) {
                Log.i(TAG, "accept: FAILED");
            }
        });







        // TODO refactor this

//        ((Button) findViewById(R.id.buttonCreateBatchNewBatch)).setOnClickListener(v -> {
//            String batchType = ((EditText) findViewById(R.id.editTextBatchTypeNewBatch)).getText().toString();
//            Integer batchNum = Integer.parseInt(((EditText) findViewById(R.id.editTextBatchNumNewBatch)).getText().toString());
//            String batchId = ((EditText) findViewById(R.id.editTextBatchIdNewBatch)).getText().toString();
//            Batch batch = Batch.builder()
//                    .batchIdentifier(batchId).batchNumber(batchNum).type(batchType).build();
//            Amplify.API.mutate(ModelMutation.create(batch),
//                    response ->{
//                        Log.i(TAG, "oncreate success");
//                    },
//                    response -> {
//                        Log.i(TAG, "onCreate: fail");
//
//                    });
//
//            Toast.makeText(this, "Started batch " + batchId, Toast.LENGTH_LONG).show();
//            startActivity(new Intent(this,TakeMeasurementActivity.class));
//        });
    }

    public void onRadioButtonClicked(View view) {
        boolean defaultFahrenheit = true;        // TODO refactor this to query a saved user setting based on selected preference
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonTempCTakeMeasurement:
                if (checked)
                    // TODO display this page in C
                    break;
            case R.id.radioButtonTempFTakeMeasurement:
                if (checked)
                    // TODO display this page in F

                    break;
        }
    }


}