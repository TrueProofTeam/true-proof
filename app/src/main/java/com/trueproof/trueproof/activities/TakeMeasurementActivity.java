package com.trueproof.trueproof.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Measure;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Batch;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_measurement);
        modifyActionbar();
        inputLimitListener();
        saveMeasurement();

    }

    public void calculateOnChange() {
        String inputTemperature = ((EditText) findViewById(R.id.editTextTemperatureMain)).getText().toString();
        if (inputTemperature != null && inputTemperature.length() > 0 && !inputTemperature.contains(".")) {
            String doubleAppend = inputTemperature + ".0";
            inTempDouble = Double.parseDouble(doubleAppend);
        }
        if (inputTemperature != null && inputTemperature.length() > 0 && inputTemperature.contains(".")) {
            inTempDouble = Double.parseDouble(inputTemperature);
        }
        System.out.println("inTempDouble = " + inTempDouble);
        ////////////////////////
        String inputTemperatureCorrection = ((EditText) findViewById(R.id.editTextTemperatureCorrectionMain)).getText().toString();
        if (inputTemperatureCorrection != null && inputTemperatureCorrection.length() > 0 && !inputTemperatureCorrection.contains(".")) {
            String doubleAppend = inputTemperatureCorrection + ".0";
            inputTempCorrDouble = Double.parseDouble(doubleAppend);
        }
        if (inputTemperatureCorrection != null && inputTemperatureCorrection.length() > 0 && inputTemperatureCorrection.contains(".")) {
            inputTempCorrDouble = Double.parseDouble(inputTemperatureCorrection);
        }
        System.out.println("inputTempCorrDouble = " + inputTempCorrDouble);
        /////////////////////////
        String inputProof = ((EditText) findViewById(R.id.editTextHydrometerMain)).getText().toString();
        if (inputProof != null && inputProof.length() > 0 && !inputProof.contains(".")) {
            String doubleAppend = inputProof + ".0";
            inputProofDouble = Double.parseDouble(doubleAppend);
        }
        if (inputProof != null && inputProof.length() > 0 && inputProof.contains(".")) {
            inputProofDouble = Double.parseDouble(inputProof);
        }
        System.out.println("inputProofDouble = " + inputProofDouble);
        /////////////////////////
        String inputProofCorrection = ((EditText) findViewById(R.id.editTextHydrometerCorrectionMain)).getText().toString();
        if (inputProofCorrection != null && inputProofCorrection.length() > 0 && !inputProofCorrection.contains(".")) {
            String doubleAppend = inputProofCorrection + ".0";
            inputProofCorrDouble = Double.parseDouble(doubleAppend);
        }
        if (inputProofCorrection != null && inputProofCorrection.length() > 0 && inputProofCorrection.contains(".")) {
            inputProofCorrDouble = Double.parseDouble(inputProofCorrection);
        }
        System.out.println("inputProofCorrDouble = " + inputProofCorrDouble);

        TextView calculatedProof = findViewById(R.id.textViewCalculatedProofMain);

        double proofFromProofing = proofing.proof(inTempDouble, inputProofDouble, inputProofCorrDouble, inputTempCorrDouble);
        if (proofFromProofing < 0) {
            calculatedProof.setText("Does not exist. Check measurements and try again.");
        } else calculatedProof.setText(String.valueOf(proofFromProofing));

    }

    public void inputLimitListener(){
        EditText tempField = findViewById(R.id.editTextTemperatureTakeMeasurement);
        InputFilterMinMax tempLimits = new InputFilterMinMax(1.0, 100.0);
        tempField.setFilters(new InputFilter[]{tempLimits});

        EditText tempCorrectionField = findViewById(R.id.editTextTempCorrectionTakeMeasurement);
        InputFilterMinMax tempCorrLimits = new InputFilterMinMax(-1.0, 1.0);
        tempCorrectionField.setFilters(new InputFilter[]{tempCorrLimits});

        EditText proofField = findViewById(R.id.editTextHydrometerTakeMeasurement);
        InputFilterMinMax proofLimits = new InputFilterMinMax(1.0, 206.0);
        proofField.setFilters(new InputFilter[]{proofLimits});

        EditText proofCorrectionField = findViewById(R.id.editTextHydroCorrectionTakeMeasurement);
        InputFilterMinMax proofCorrLimits = new InputFilterMinMax(-1.0, 1.0);
        proofCorrectionField.setFilters(new InputFilter[]{proofCorrLimits});

        tempField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateOnChange();
            }
        });

        tempCorrectionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateOnChange();
            }
        });

        proofField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateOnChange();
            }
        });

        proofCorrectionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateOnChange();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveMeasurement(){
        ((Button) findViewById(R.id.buttonSaveMeasurementTakeMeasurement)).setOnClickListener(v -> {
            String temptToSave = ((TextView) findViewById(R.id.editTextTemperatureTakeMeasurement))
                    .getText().toString();
            System.out.println("temptToSave = " + temptToSave);

            String tempCorrectionToSave = ((TextView) findViewById(R.id.editTextTempCorrectionTakeMeasurement))
                    .getText().toString();
            System.out.println("tempCorrectionToSave = " + tempCorrectionToSave);

            String hydroToSave = ((TextView) findViewById(R.id.editTextHydrometerTakeMeasurement))
                    .getText().toString();
            System.out.println("hydroToSave = " + hydroToSave);

            String hydroCorrectionToSave = ((TextView) findViewById(R.id.editTextHydroCorrectionTakeMeasurement))
                    .getText().toString();
            System.out.println("hydroCorrectionToSave = " + hydroCorrectionToSave);

            String measurementToSave = ((TextView) findViewById(R.id.textViewCalculatedProofTakeMeasurement))
                    .getText().toString();
            System.out.println("measurementToSave = " + measurementToSave);

            String measurementTime = userLocalTime();
            System.out.println("measurementTime = " + measurementTime);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String userLocalTime(){

        TimeZone timeZone = TimeZone.getDefault();
        return ZonedDateTime.now(ZoneId.of(timeZone.getID()))
                .format(
                        DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM)
                                .withLocale(Locale.US)
                );
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.nav_settings)TakeMeasurementActivity.this.startActivity(new Intent(TakeMeasurementActivity.this, SettingsActivity.class));
        if (menuItem.getItemId() == R.id.nav_batch_list)TakeMeasurementActivity.this.startActivity(new Intent(TakeMeasurementActivity.this, BatchListActivity.class));
        if (menuItem.getItemId() == R.id.nav_quick_calculator)TakeMeasurementActivity.this.startActivity(new Intent(TakeMeasurementActivity.this, MainActivity.class));
        if (menuItem.getItemId() == R.id.nav_log_out){
            Amplify.Auth.signOut(
                    ()->{
                        Log.i(TAG,"Success Logout!");
                    },
                    r->{});
            TakeMeasurementActivity.this.startActivity(new Intent( TakeMeasurementActivity.this,MainActivity.class));
            finish();
        }
        return true;
    }


    private void modifyActionbar () {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle("Take Measurement");
    }
}
