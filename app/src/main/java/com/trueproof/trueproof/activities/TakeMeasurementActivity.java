package com.trueproof.trueproof.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Measure;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.logic.InputFilterMinMax;
import com.trueproof.trueproof.logic.Proofing;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.DistilleryRepository;
import com.trueproof.trueproof.utils.MeasurementRepository;
import com.trueproof.trueproof.utils.TestDependencyInjection;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.inject.Inject;


import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TakeMeasurementActivity extends AppCompatActivity {

    @Inject
    Proofing proofing;

    double inTempDouble = 0.0;
    double inputTempCorrDouble = 0.0;
    double inputProofDouble = 0.0;
    double inputProofCorrDouble = 0.0;
  
    static String TAG = "t.takeMeasurement";

    @Inject
    DistilleryRepository distilleryRepository;

    @Inject
    BatchRepository batchRepository;

    @Inject
    MeasurementRepository measurementRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_measurement);

        EditText tempField = findViewById(R.id.editTextTemperatureTakeMeasurement);
        InputFilterMinMax tempLimits = new InputFilterMinMax(1.0, 100.0);
        tempField.setFilters(new InputFilter[]{ tempLimits });

        EditText tempCorrectionField = findViewById(R.id.editTextTemperatureTakeMeasurement);
        InputFilterMinMax tempCorrLimits = new InputFilterMinMax(-1.0, 1.0);
        tempCorrectionField.setFilters(new  InputFilter[]{ tempCorrLimits });

        EditText proofField = findViewById(R.id.editTextHydrometerTakeMeasurement);
        InputFilterMinMax proofLimits = new InputFilterMinMax(1.0, 206.0);
        proofField.setFilters(new  InputFilter[]{ proofLimits });

        EditText proofCorrectionField = findViewById(R.id.editTextHydroCorrectionTakeMeasurement);
        InputFilterMinMax proofCorrLimits = new InputFilterMinMax(-1.0, 1.0);
        proofCorrectionField.setFilters(new  InputFilter[]{ proofCorrLimits });

        tempField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {calculateOnChange();}
        });

        tempCorrectionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {calculateOnChange();}
        });

        proofField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {calculateOnChange();}
        });

        proofCorrectionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {calculateOnChange();
            }
        });

        }

    public void calculateOnChange(){
        String inputTemperature = ((EditText) findViewById(R.id.editTextTemperatureMain)).getText().toString();
        if (inputTemperature != null && inputTemperature.length() > 0 && !inputTemperature.contains(".")){
            String doubleAppend = inputTemperature + ".0";
            inTempDouble = Double.parseDouble(doubleAppend);
        }
        if (inputTemperature != null && inputTemperature.length() > 0 && inputTemperature.contains(".")){
            inTempDouble = Double.parseDouble(inputTemperature);
        }
        System.out.println("inTempDouble = " + inTempDouble);
        ////////////////////////
        String inputTemperatureCorrection = ((EditText) findViewById(R.id.editTextTemperatureCorrectionMain)).getText().toString();
        if (inputTemperatureCorrection != null && inputTemperatureCorrection.length() > 0 && !inputTemperatureCorrection.contains(".")){
            String doubleAppend = inputTemperatureCorrection + ".0";
            inputTempCorrDouble = Double.parseDouble(doubleAppend);
        }
        if (inputTemperatureCorrection != null && inputTemperatureCorrection.length() > 0 && inputTemperatureCorrection.contains(".")) {
            inputTempCorrDouble = Double.parseDouble(inputTemperatureCorrection);
        }
        System.out.println("inputTempCorrDouble = " + inputTempCorrDouble);
        /////////////////////////
        String inputProof = ((EditText) findViewById(R.id.editTextHydrometerMain)).getText().toString();
        if (inputProof != null && inputProof.length() > 0 && !inputProof.contains(".")){
            String doubleAppend = inputProof + ".0";
            inputProofDouble = Double.parseDouble(doubleAppend);
        }
        if (inputProof != null && inputProof.length() > 0 && inputProof.contains(".")) {
            inputProofDouble = Double.parseDouble(inputProof);
        }
        System.out.println("inputProofDouble = " + inputProofDouble);
        /////////////////////////
        String inputProofCorrection = ((EditText) findViewById(R.id.editTextHydrometerCorrectionMain)).getText().toString();
        if (inputProofCorrection != null && inputProofCorrection.length() > 0 && !inputProofCorrection.contains(".")){
            String doubleAppend = inputProofCorrection + ".0";
            inputProofCorrDouble = Double.parseDouble(doubleAppend);
        }
        if (inputProofCorrection != null && inputProofCorrection.length() > 0 && inputProofCorrection.contains(".")) {
            inputProofCorrDouble = Double.parseDouble(inputProofCorrection);
        }
        System.out.println("inputProofCorrDouble = " + inputProofCorrDouble);

        TextView calculatedProof = findViewById(R.id.textViewCalculatedProofMain);

        double proofFromProofing = proofing.proof(inTempDouble, inputProofDouble, inputProofCorrDouble, inputTempCorrDouble);
        if (proofFromProofing < 0){
            calculatedProof.setText("Does not exist. Check measurements and try again.");
        }else calculatedProof.setText(String.valueOf(proofFromProofing));

    }
    
}




//        // temp dummy data; TODO refactor with user settings
//        Distillery distillery = Distillery.builder().name("Old Tyme Whiskey").dspId("WTF202020").build();
//        Batch batch = Batch.builder().distillery(distillery).build();
//        Measurement measurement = Measurement.builder().trueProof(60.0).temperature(30.0).hydrometer(10.0).temperatureCorrection(0.1).hydrometerCorrection(0.1).build();
//
//        // TODO display batch type on R.id.textViewBatchNumberTakeMeasurment
//        // TODO read value of C/F radio button when submitting data
//        // TODO display PROOF AT 60F on R.id.textViewCalculatedProofTakeMeasurement
//        Integer batchNum = 0;
//
//        ((Button) findViewById(R.id.buttonSaveMeasurementTakeMeasurement)).setOnClickListener(v -> {
//            Double temp = Double.parseDouble(((EditText) findViewById(R.id.editTextTemperatureTakeMeasurement)).getText().toString());
//            Double tempCorr = Double.parseDouble(((EditText) findViewById(R.id.editTextTempCorrectionTakeMeasuremen)).getText().toString());
//            Double hydro = Double.parseDouble(((EditText) findViewById(R.id.editTextHydrometerTakeMeasurement)).getText().toString());
//            Double hydroCorr = Double.parseDouble(((EditText) findViewById(R.id.editTextHydroCorrectionTakeMeasurement)).getText().toString());
//
//            distilleryRepository.saveDistillery(distillery,
//                    r -> {
//                        Log.i(TAG, "onCreate: distillery created");
//                        batchRepository.saveBatch(batch,
//                                r2 -> {
//                                    Log.i(TAG, "onCreate: batch successful");
//                                    measurementRepository.saveMeasurement(measurement,
//
//                                            r3 -> {
//                                                Log.i(TAG, "onCreate: measurement created");
//                                                Toast.makeText(this, "Saved measurement for " + batchNum, Toast.LENGTH_LONG).show();
//                                                startActivity(new Intent(this, TakeMeasurementActivity.class));
//
//                                            },
//
//                                            r3 -> {
//                                                Log.i(TAG, "onCreate: error on savemeasurement");
//                                            });
//                                },
//                                r2 -> {
//                                    Log.i(TAG, "onCreate: error on batch");
//                                });
//                    },
//                    r -> {
//                        Log.i(TAG, "onCreate: error on distillery");
//                    }
//            );
//            Consumer consumer = new Consumer() {
//                @Override
//                public void accept(@NonNull @NotNull Object value) {
//                    Log.i(TAG, "accept: WORKS");
//                }
//            };
//            measurementRepository.saveMeasurement(measurement, consumer, new Consumer<ApiException>() {
//                @Override
//                public void accept(@NonNull @NotNull ApiException value) {
//                    Log.i(TAG, "accept: FAILED");
//                }
//            });
//
//        });
//    }
//
//    public void onRadioButtonClicked(View view) {
//        boolean defaultFahrenheit = true;        // TODO refactor this to query a saved user setting based on selected preference
//        boolean checked = ((RadioButton) view).isChecked();
//        switch (view.getId()) {
//            case R.id.radioButtonTempCTakeMeasurement:
//                if (checked)
//                    // TODO display this page in C
//                    break;
//            case R.id.radioButtonTempFTakeMeasurement:
//                if (checked)
//                    // TODO display this page in F
//
//                    break;
//        }
//    }


//}