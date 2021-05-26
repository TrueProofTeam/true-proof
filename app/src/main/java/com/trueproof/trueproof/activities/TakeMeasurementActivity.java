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
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.DistilleryRepository;
import com.trueproof.trueproof.utils.MeasurementRepository;

import org.jetbrains.annotations.NotNull;

public class TakeMeasurementActivity extends AppCompatActivity {

    static String TAG = "t.takeMeasurement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_measurement);

        // temp dummy data
        Distillery distillery = Distillery.builder().name("Old Tyme Whiskey").dspId("WTF202020").build();
        Batch batch = Batch.builder().distillery(distillery).build();
        Measurement measurement = Measurement.builder().trueProof(60.0).temperature(30.0).hydrometer(10.0).temperatureCorrection(0.1).hydrometerCorrection(0.1).build();
        DistilleryRepository distilleryRepository = new DistilleryRepository();
        BatchRepository batchRepository = new BatchRepository();
        MeasurementRepository measurementRepository = new MeasurementRepository();

        // TODO display batch type on R.id.textViewBatchNumberTakeMeasurment
        // TODO read value of C/F radio button when submitting data
        // TODO display PROOF AT 60F on R.id.textViewCalculatedProofTakeMeasurement
        Integer batchNum = 0;

        ((Button) findViewById(R.id.buttonSaveMeasurementTakeMeasurement)).setOnClickListener(v -> {
            Double temp = Double.parseDouble(((EditText) findViewById(R.id.editTextTemperatureTakeMeasurement)).getText().toString());
            Double tempCorr = Double.parseDouble(((EditText) findViewById(R.id.editTextTempCorrectionTakeMeasuremen)).getText().toString());
            Double hydro = Double.parseDouble(((EditText) findViewById(R.id.editTextHydrometerTakeMeasurement)).getText().toString());
            Double hydroCorr = Double.parseDouble(((EditText) findViewById(R.id.editTextHydroCorrectionTakeMeasurement)).getText().toString());


            distilleryRepository.saveDistillery(distillery,
                    r -> {
                        Log.i(TAG, "onCreate: distillery created");
                        batchRepository.saveBatch(batch,
                                r2 -> {
                                    Log.i(TAG, "onCreate: batch successful");
                                    measurementRepository.saveMeasurement(measurement,

                                            r3 -> {
                                                Log.i(TAG, "onCreate: measurement created");
                                                Toast.makeText(this, "Saved measurement for " + batchNum, Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(this, TakeMeasurementActivity.class));

                                            },

                                            r3 -> {
                                                Log.i(TAG, "onCreate: error on savemeasurement");
                                            });
                                },
                                r2 -> {
                                    Log.i(TAG, "onCreate: error on batch");
                                });
                    },
                    r -> {
                        Log.i(TAG, "onCreate: error on distillery");
                    }
            );
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

        });
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