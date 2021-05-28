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
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.amplifyframework.datastore.generated.model.User;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.adapters.MeasurementListAdapter;
import com.trueproof.trueproof.logic.InputFilterMinMax;
import com.trueproof.trueproof.logic.Proofing;
import com.trueproof.trueproof.utils.ActivityUtils;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.DistilleryRepository;
import com.trueproof.trueproof.utils.MeasurementRepository;
import com.trueproof.trueproof.utils.TestDependencyInjection;
import com.trueproof.trueproof.utils.UserSettings;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;


import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TakeMeasurementActivity extends AppCompatActivity implements MeasurementListAdapter.OnClickHandler {

    EditText tempField;
    InputFilterMinMax tempLimits;

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
    @Inject
    ActivityUtils activityUtils;
    @Inject
    UserSettings userSettings;


    User user;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_measurement);
        Distillery distillery = userSettings.getCachedDistillery();
        if (distillery != null)((TextView)findViewById(R.id.textViewTakeMeasurementdsp)).setText(distillery.getName());
        else ((TextView)findViewById(R.id.textViewBatchListdsp)).setText("Untitled Distillery");
        inputLimitListener();
        saveMeasurement();

        ((Button) findViewById(R.id.buttonSaveMeasurementTakeMeasurement))
                .setEnabled(false);

        user = userSettings.getCachedUserSettings();
        user = User.builder().defaultTemperatureUnit(TemperatureUnit.FAHRENHEIT)
                .defaultHydrometerCorrection(0.0)
                .defaultTemperatureCorrection(0.0)
                .build();

        if (user.getDefaultTemperatureUnit().equals(TemperatureUnit.CELSIUS)) {
            tempLimits = new InputFilterMinMax(-17.22, 37.78);
            tempField.setFilters(new InputFilter[]{tempLimits});
        }

    }

    @Override
    public void onClick(Measurement measurement){
        double temp = measurement.getTemperature();
        double tempCorrection = measurement.getTemperatureCorrection();
        double hydro = measurement.getHydrometer();
        double hydroCorrection = measurement.getHydrometerCorrection();
        double trueProof = measurement.getTrueProof();
        Intent viewMeasurementDetail = new Intent(TakeMeasurementActivity.this, MeasurementDetailActivity.class);
        viewMeasurementDetail.putExtra("temp", temp);
        viewMeasurementDetail.putExtra("tempCorrection", tempCorrection);
        viewMeasurementDetail.putExtra("hydro", hydro);
        viewMeasurementDetail.putExtra("hydroCorrection", hydroCorrection);
        viewMeasurementDetail.putExtra("trueProof", trueProof);
        startActivity(viewMeasurementDetail);
    }

    public void calculateOnChange() {
        String inputTemperature = ((EditText) findViewById(R.id.editTextTemperatureTakeMeasurement)).getText().toString();
        if (inputTemperature.length() > 0 && !inputTemperature.contains(".")) {
            String doubleAppend = inputTemperature + ".0";
            inTempDouble = Double.parseDouble(doubleAppend);
        }
        if (inputTemperature.length() > 0 && inputTemperature.contains(".")) {
            inTempDouble = Double.parseDouble(inputTemperature);
        }
        if (user.getDefaultTemperatureUnit().equals(TemperatureUnit.CELSIUS) && inputTemperature.length() > 0) {
            System.out.println("calculate on change, C->F conversion");
            double getTemp = Double.parseDouble(tempField.getText().toString()) + inputTempCorrDouble;
            double convertTemp = ((getTemp * 1.8) + 32);
            BigDecimal roundTemp = new BigDecimal(convertTemp);
            MathContext decimalPlaces = new MathContext(4);
            BigDecimal rounded = roundTemp.round(decimalPlaces);
            inTempDouble = Double.parseDouble(String.valueOf(rounded));
        }
        System.out.println("inTempDouble = " + inTempDouble);
        ////////////////////////
        String inputTemperatureCorrection = ((EditText) findViewById(R.id.editTextTempCorrectionTakeMeasurement)).getText().toString();
        if (inputTemperatureCorrection.length() > 0 && !inputTemperatureCorrection.contains(".")) {
            String doubleAppend = inputTemperatureCorrection + ".0";
            inputTempCorrDouble = Double.parseDouble(doubleAppend);
        }
        if (inputTemperatureCorrection.length() > 0 && inputTemperatureCorrection.startsWith(".")) {
            String appendDot = "0" + inputTemperatureCorrection;
            inputTempCorrDouble = Double.parseDouble(appendDot);
        } else if (inputTemperatureCorrection.length() > 0 && inputTemperatureCorrection.contains(".")) {
            inputTempCorrDouble = Double.parseDouble(inputTemperatureCorrection);
        }
        System.out.println("inputTempCorrDouble = " + inputTempCorrDouble);
        /////////////////////////
        String inputProof = ((EditText) findViewById(R.id.editTextHydrometerTakeMeasurement)).getText().toString();
        if (inputProof.length() > 0 && !inputProof.contains(".")) {
            String doubleAppend = inputProof + ".0";
            inputProofDouble = Double.parseDouble(doubleAppend);
        }
        if (inputProof.length() > 0 && inputProof.contains(".")) {
            inputProofDouble = Double.parseDouble(inputProof);
        }
        System.out.println("inputProofDouble = " + inputProofDouble);
        /////////////////////////
        String inputProofCorrection = ((EditText) findViewById(R.id.editTextHydroCorrectionTakeMeasurement)).getText().toString();
        if (inputProofCorrection.length() > 0 && !inputProofCorrection.contains(".")) {
            String doubleAppend = inputProofCorrection + ".0";
            inputProofCorrDouble = Double.parseDouble(doubleAppend);
        }
        if (inputProofCorrection.length() > 0 && inputProofCorrection.startsWith(".")) {
            String appendDot = "0" + inputProofCorrection;
            inputTempCorrDouble = Double.parseDouble(appendDot);
        } else if (inputProofCorrection.length() > 0 && inputProofCorrection.contains(".")) {
            inputTempCorrDouble = Double.parseDouble(inputProofCorrection);
        }
        System.out.println("inputProofCorrDouble = " + inputProofCorrDouble);

        TextView calculatedProof = findViewById(R.id.textViewCalculatedProofTakeMeasurement);

        double proofFromProofing = proofing.proof(inTempDouble, inputProofDouble, inputProofCorrDouble, inputTempCorrDouble);
        if (proofFromProofing < 1.7) {
            calculatedProof.setText("Invalid Measurements");
//            ((Button) findViewById(R.id.buttonSaveMeasurementTakeMeasurement))
//                    .setEnabled(false);
        } else {
            calculatedProof.setText(String.valueOf(proofFromProofing));
            ((Button) findViewById(R.id.buttonSaveMeasurementTakeMeasurement))
                    .setEnabled(true);
        }
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

        tempField.addTextChangedListener(getWatcher());
        tempCorrectionField.addTextChangedListener(getWatcher());
        proofField.addTextChangedListener(getWatcher());
        proofCorrectionField.addTextChangedListener(getWatcher());
    }

    @NotNull
    private TextWatcher getWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateOnChange();
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveMeasurement(){
        ((Button) findViewById(R.id.buttonSaveMeasurementTakeMeasurement)).setOnClickListener(v -> {
            String temptToSave = ((TextView) findViewById(R.id.editTextTemperatureTakeMeasurement))
                    .getText().toString();
            System.out.println("temptToSave = " + temptToSave);

            String tempCorrectionToSave = ((TextView) findViewById(R.id.editTextTempCorrectionTakeMeasurement))
                    .getText().toString();
            if (tempCorrectionToSave.isEmpty()){tempCorrectionToSave = "0.0";}
            System.out.println("tempCorrectionToSave = " + tempCorrectionToSave);

            String hydroToSave = ((TextView) findViewById(R.id.editTextHydrometerTakeMeasurement))
                    .getText().toString();
            System.out.println("hydroToSave = " + hydroToSave);

            String hydroCorrectionToSave = ((TextView) findViewById(R.id.editTextHydroCorrectionTakeMeasurement))
                    .getText().toString();
            if (hydroCorrectionToSave.isEmpty()){hydroCorrectionToSave = "0.0";}
            System.out.println("hydroCorrectionToSave = " + hydroCorrectionToSave);

            String measurementToSave = ((TextView) findViewById(R.id.textViewCalculatedProofTakeMeasurement))
                    .getText().toString();
            System.out.println("measurementToSave = " + measurementToSave);

            Measurement measurement = Measurement.builder()
                    .trueProof(Double.parseDouble(measurementToSave))
                    .temperature(Double.parseDouble(temptToSave))
                    .hydrometer(Double.parseDouble(hydroToSave))
                    .temperatureCorrection(Double.parseDouble(tempCorrectionToSave))
                    .hydrometerCorrection(Double.parseDouble(hydroCorrectionToSave))
                    .build();

            System.out.println("measurement = " + measurement);

            Amplify.API.mutate(
                    ModelMutation.create(measurement),
                    response -> Log.i("Mutate", "success"),
                    error -> Log.e("Mutate", "error " + error)
            );
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
        return activityUtils.onOptionsItemSelected(this, menuItem);
    }

}
