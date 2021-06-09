package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.logic.Proofing;
import com.trueproof.trueproof.utils.ActivityUtils;
import com.trueproof.trueproof.utils.UserSettings;
import com.trueproof.trueproof.viewmodels.MainActivityViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends MeasurementActivity {
    static final String TAG = "TrueProof.MainActivity";
    @Inject Proofing proofing;
    @Inject ActivityUtils activityUtils;
    @Inject UserSettings userSettings;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        userSettings.refreshCache(r -> {
                    Log.i("TrueProofApplication", "Cache updated");
                } , e -> Log.e("TrueProofApplication", "Error updating cache")
        );

        setupMeasurementActivity();

        initializeLoginButton();

        ((TextView) findViewById(R.id.textViewTermsOfUseMain)).setOnClickListener(v -> {
            Intent intent = new Intent(this, TermsOfUseActivity.class);
            startActivity(intent);
        });
    }
    
    @Override
    void saveMeasurementViews() {
        temperatureEditText = findViewById(R.id.editTextTemperatureMain);
        temperatureCorrectionEditText = findViewById(R.id.editTextTemperatureCorrectionMain);
        hydrometerEditText = findViewById(R.id.editTextHydrometerMain);
        hydrometerCorrectionEditText = findViewById(R.id.editTextHydrometerCorrectionMain);
        trueProofText = findViewById(R.id.textViewCalculatedProofMain);
        temperatureText = findViewById(R.id.textViewTempMain);
        temperatureCorrectionText = findViewById(R.id.textViewTempCorrectionMain);
        temperatureUnitRadioGroup = findViewById(R.id.radioGroupMain);
        temperatureUnitCRadioId = R.id.radioButtonTempCMain;
        temperatureUnitFRadioId = R.id.radioButtonTempFMain;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        AuthUser principal = Amplify.Auth.getCurrentUser();
        if (principal != null) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        } else return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return activityUtils.onOptionsItemSelected(this, menuItem);
    }

    private void initializeLoginButton() {
        AuthUser principal = Amplify.Auth.getCurrentUser();

        Button loginButton = findViewById(R.id.buttonLoginMain);
        if (principal == null) {

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            loginButton.setText("Go to Batch List");
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, BatchListActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
