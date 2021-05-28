package com.trueproof.trueproof.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
import com.trueproof.trueproof.models.DistilleryUtils;
import com.trueproof.trueproof.utils.AWSDateTime;
import com.trueproof.trueproof.utils.ActivityUtils;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.DistilleryRepository;
import com.trueproof.trueproof.utils.MeasurementRepository;
import com.trueproof.trueproof.utils.TestDependencyInjection;
import com.trueproof.trueproof.utils.UserSettings;
import com.trueproof.trueproof.viewmodels.BatchListViewModel;
import com.trueproof.trueproof.viewmodels.TakeMeasurementViewModel;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
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
@RequiresApi(api = Build.VERSION_CODES.O)
public class TakeMeasurementActivity extends AppCompatActivity {
    public static final String BATCH_JSON = "batch_json";

    double inTempDouble = 0.0;
    double inputTempCorrDouble = 0.0;
    double inputProofDouble = 0.0;
    double inputProofCorrDouble = 0.0;

    static String TAG = "t.takeMeasurement";

    @Inject
    Proofing proofing;
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

    TakeMeasurementViewModel viewModel;
    User user;

    EditText temperatureEditText;
    EditText temperatureCorrectionEditText;
    EditText hydrometerEditText;
    EditText hydrometerCorrectionEditText;
    TextView trueProofText;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_measurement);
        viewModel = new ViewModelProvider(this).get(TakeMeasurementViewModel.class);

        Distillery distillery = userSettings.getCachedDistillery();
        TextView distilleryName = findViewById(R.id.textViewTakeMeasurementdsp);
        distilleryName.setText(DistilleryUtils.toHeaderString(distillery));

        saveViews();
        getBatchFromIntent();
        inputLimitListener();
        saveMeasurement();

        ((Button) findViewById(R.id.buttonSaveMeasurementTakeMeasurement))
                .setEnabled(false);

        user = userSettings.getCachedUserSettings();
        if (user == null)
            user = User.builder().defaultTemperatureUnit(TemperatureUnit.FAHRENHEIT)
                    .defaultHydrometerCorrection(0.0)
                    .defaultTemperatureCorrection(0.0)
                    .build();

        if (user.getDefaultTemperatureUnit().equals(TemperatureUnit.CELSIUS)) {
            temperatureEditText.setFilters(new InputFilter[]{new InputFilterMinMax(-17.22, 37.78)});
        }
    }

    private void saveViews() {
        Log.i(TAG, "Temperature edit text: " + temperatureEditText);
        temperatureEditText = findViewById(R.id.editTextTemperatureTakeMeasurement);
        temperatureCorrectionEditText = findViewById(R.id.editTextTempCorrectionTakeMeasurement);
        hydrometerEditText = findViewById(R.id.editTextHydrometerTakeMeasurement);
        hydrometerCorrectionEditText = findViewById(R.id.editTextHydrometerTakeMeasurement);
        trueProofText = findViewById(R.id.textViewCalculatedProofTakeMeasurement);
    }

    private void getBatchFromIntent() {
        Intent intent = getIntent();
        viewModel.setBatchFromJson(intent.getStringExtra(BATCH_JSON));
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
            double getTemp = Double.parseDouble(temperatureEditText.getText().toString()) + inputTempCorrDouble;
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforeTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: ");
                calculateOnChange();
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveMeasurement() {
        ((Button) findViewById(R.id.buttonSaveMeasurementTakeMeasurement)).setOnClickListener(v -> {
            String temperature = ((TextView) findViewById(R.id.editTextTemperatureTakeMeasurement))
                    .getText().toString();
            System.out.println("temptToSave = " + temperature);

            String temperatureCorrection = ((TextView) findViewById(R.id.editTextTempCorrectionTakeMeasurement))
                    .getText().toString();
            if (temperatureCorrection.isEmpty()){temperatureCorrection = "0.0";}
            System.out.println("tempCorrectionToSave = " + temperatureCorrection);

            String hydrometer = ((TextView) findViewById(R.id.editTextHydrometerTakeMeasurement))
                    .getText().toString();
            System.out.println("hydroToSave = " + hydrometer);

            String hydrometerCorrection = ((TextView) findViewById(R.id.editTextHydroCorrectionTakeMeasurement))
                    .getText().toString();
            if (hydrometerCorrection.isEmpty()){hydrometerCorrection = "0.0";}
            System.out.println("hydroCorrectionToSave = " + hydrometerCorrection);

            String trueProof = ((TextView) findViewById(R.id.textViewCalculatedProofTakeMeasurement))
                    .getText().toString();
            System.out.println("measurementToSave = " + trueProof);

            Measurement measurement = Measurement.builder()
                    .trueProof(Double.parseDouble(trueProof))
                    .temperature(Double.parseDouble(temperature))
                    .hydrometer(Double.parseDouble(hydrometer))
                    .temperatureCorrection(Double.parseDouble(temperatureCorrection))
                    .hydrometerCorrection(Double.parseDouble(hydrometerCorrection))
                    .build();

            measurementRepository.saveMeasurement(measurement,
                    r -> {
                        Log.i(TAG, "saveMeasurement: success " + r);
                    },
                    e -> {
                        Log.e(TAG, "saveMeasurement: failure " + e);
                    });

            viewModel.saveMeasurement(measurement);
        });
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
