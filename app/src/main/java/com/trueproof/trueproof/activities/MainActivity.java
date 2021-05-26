package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.Distillery;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.logic.InputFilterMinMax;
import com.trueproof.trueproof.logic.Proofing;
import com.trueproof.trueproof.utils.TestDependencyInjection;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    static final String TAG = "TrueProof.MainActivity";

    @Inject
    TestDependencyInjection testDependencyInjection;

    @Inject
    Proofing proofing;

    double inTempDouble = 0.0;
    double inputTempCorrDouble = 0.0;
    double inputProofDouble = 0.0;
    double inputProofCorrDouble = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeLoginButton();
        Log.i(TAG, "onCreate: " + testDependencyInjection.hello());
        Log.i(TAG, "onCreate: " + proofing.proof(80.5, 100.1, 1.1, 1.1));

        limitAndCalculate();

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


//        calculatedProof.setText(String.valueOf(proofing.proof(inTempDouble, inputProofDouble, inputProofCorrDouble, inputTempCorrDouble)));
    }

    public void limitAndCalculate(){
        EditText tempField = findViewById(R.id.editTextTemperatureMain);
        InputFilterMinMax tempLimits = new InputFilterMinMax(1.0, 100.0);
        tempField.setFilters(new  InputFilter[]{ tempLimits });

        EditText tempCorrectionField = findViewById(R.id.editTextTemperatureCorrectionMain);
        InputFilterMinMax tempCorrLimits = new InputFilterMinMax(-1.0, 1.0);
        tempCorrectionField.setFilters(new  InputFilter[]{ tempCorrLimits });

        EditText proofField = findViewById(R.id.editTextHydrometerMain);
        InputFilterMinMax proofLimits = new InputFilterMinMax(1.0, 206.0);
        proofField.setFilters(new  InputFilter[]{ proofLimits });

        EditText proofCorrectionField = findViewById(R.id.editTextHydrometerCorrectionMain);
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

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.nav_settings)MainActivity.this.startActivity(new Intent(MainActivity.this, DistillerySettingsActivity.class));
        if (menuItem.getItemId() == R.id.nav_batch_list)MainActivity.this.startActivity(new Intent(MainActivity.this, BatchListActivity.class));
        if (menuItem.getItemId() == R.id.nav_settings)MainActivity.this.startActivity(new Intent(MainActivity.this, DistillerySettingsActivity.class));
        if (menuItem.getItemId() == R.id.nav_quick_calculator)MainActivity.this.startActivity(new Intent(MainActivity.this, TakeMeasurementActivity.class));
        return true;
        }

    void initializeLoginButton() {
        Button loginButton = findViewById(R.id.buttonLoginMain);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

    }

}
