package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.amplifyframework.datastore.generated.model.User;

import com.trueproof.trueproof.R;
import com.trueproof.trueproof.models.BatchUtils;
import com.trueproof.trueproof.models.DistilleryUtils;
import com.trueproof.trueproof.utils.ActivityUtils;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.DistilleryRepository;
import com.trueproof.trueproof.utils.MeasurementRepository;
import com.trueproof.trueproof.utils.UserSettings;
import com.trueproof.trueproof.viewmodels.TakeMeasurementViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@RequiresApi(api = Build.VERSION_CODES.O)
public class TakeMeasurementActivity extends MeasurementActivity {
    public static final String BATCH_JSON = "batch_json";
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

    TakeMeasurementViewModel viewModel;
    User user;

    private Button saveMeasurementButton;
    private Button goToBatchDetailButton;
    private TextView batchNumberText;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_measurement);
        viewModel = new ViewModelProvider(this).get(TakeMeasurementViewModel.class);
        super.viewModel = viewModel;

        saveViews();
        getBatchFromIntent();
        initialize();
        setupMeasurementActivity();
        observeSavedLiveData();
    }

    private void saveViews() {
        saveMeasurementButton = findViewById(R.id.buttonSaveMeasurementTakeMeasurement);
        goToBatchDetailButton = findViewById(R.id.buttonBatchDetailTakeMeasurement);
        batchNumberText = findViewById(R.id.textViewBatchNumberTakeMeasurment);
    }

    private void initialize() {
        saveMeasurementButton.setEnabled(false);

        Distillery distillery = userSettings.getCachedDistillery();
        TextView distilleryName = findViewById(R.id.textViewTakeMeasurementdsp);
        distilleryName.setText(DistilleryUtils.toHeaderString(distillery));

        user = userSettings.getCachedUserSettings();
        if (user == null) {
            user = User.builder().defaultTemperatureUnit(TemperatureUnit.FAHRENHEIT)
                    .defaultHydrometerCorrection(0.0)
                    .defaultTemperatureCorrection(0.0)
                    .build();
        }

        viewModel.setUserSettings(user);

        saveMeasurementButton.setOnClickListener(v -> viewModel.saveMeasurement());
        goToBatchDetailButton.setOnClickListener(v -> finish());
    }

    private void getBatchFromIntent() {
        Intent intent = getIntent();
        viewModel.setBatchFromJson(intent.getStringExtra(BATCH_JSON));

        String batchString = BatchUtils.batchToString(viewModel.getBatch());
        batchNumberText.setText(batchString);
    }

    @Override
    void saveMeasurementViews() {
        temperatureText = findViewById(R.id.textViewTemperatureTakeMeasurement);
        temperatureCorrectionText = findViewById(R.id.textViewTempCorrectionTakeMeasurement);
        temperatureEditText = findViewById(R.id.editTextTemperatureTakeMeasurement);
        temperatureCorrectionEditText = findViewById(R.id.editTextTempCorrectionTakeMeasurement);
        hydrometerEditText = findViewById(R.id.editTextHydrometerTakeMeasurement);
        hydrometerCorrectionEditText = findViewById(R.id.editTextHydroCorrectionTakeMeasurement);
        trueProofText = findViewById(R.id.textViewCalculatedProofTakeMeasurement);
    }

    @Override
    void onInvalidTrueProofCalculation() {
        super.onInvalidTrueProofCalculation();
        saveMeasurementButton.setEnabled(false);
    }

    @Override
    void onValidTrueProofCalculation(double proof) {
        super.onValidTrueProofCalculation(proof);
        saveMeasurementButton.setEnabled(true);
    }

    private void observeSavedLiveData() {
        viewModel.getSavedLiveData().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Measurement saved!", Toast.LENGTH_LONG).show();
                resetFields();
            }
            if (!success) {
                Toast.makeText(TakeMeasurementActivity.this,
                        "Error saving the measurement. Check your network connection or try again.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void resetFields() {
        temperatureEditText.setText("");
        temperatureCorrectionEditText.setText("");
        hydrometerEditText.setText("");
        hydrometerCorrectionEditText.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return activityUtils.onOptionsItemSelected(this, menuItem);
    }

}