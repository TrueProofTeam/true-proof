package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.logic.InputFilterMinMax;
import com.trueproof.trueproof.logic.Proofing;
import com.trueproof.trueproof.utils.ActivityUtils;
import com.trueproof.trueproof.utils.TestDependencyInjection;
import com.trueproof.trueproof.utils.UserSettings;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    static final String TAG = "TrueProof.MainActivity";

    EditText tempField;
    InputFilterMinMax tempLimits;

    @Inject
    TestDependencyInjection testDependencyInjection;
    @Inject
    Proofing proofing;
    @Inject
    ActivityUtils activityUtils;
    @Inject
    UserSettings userSettings;

    TemperatureUnit temperatureUnit;

    double inTempDouble = 0.0;
    double inputTempCorrDouble = 0.0;
    double inputProofDouble = 0.0;
    double inputProofCorrDouble = 0.0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeLoginButton();

        temperatureUnit = TemperatureUnit.FAHRENHEIT;

        limitAndCalculate();

        //TextView dateTimeLocal = findViewById(R.id.textViewDateTimeLocal);
        //dateTimeLocal.setText(userLocalTime());

        userSettings.refreshCache(r -> {
                    Log.i("TrueProofApplication", "works i guess");
                } , e -> Log.e("TrueProofApplication", "Error idk")
        );


    }

    public void calculateOnChange() {
        String inputTemperature = ((EditText) findViewById(R.id.editTextTemperatureMain)).getText().toString();
        if (inputTemperature.length() > 0 && !inputTemperature.contains(".")) {
            String doubleAppend = inputTemperature + ".0";
            inTempDouble = Double.parseDouble(doubleAppend);
        }
        if (inputTemperature.length() > 0 && inputTemperature.contains(".")) {
            inTempDouble = Double.parseDouble(inputTemperature);
        }
        RadioButton radioButtonC = findViewById(R.id.radioButtonTempCMain);
        if (radioButtonC.isChecked() && inputTemperature.length() > 0) {
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
        String inputTemperatureCorrection = ((EditText) findViewById(R.id.editTextTemperatureCorrectionMain)).getText().toString();
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
        String inputProof = ((EditText) findViewById(R.id.editTextHydrometerMain)).getText().toString();
        if (inputProof.length() > 0 && !inputProof.contains(".")) {
            String doubleAppend = inputProof + ".0";
            inputProofDouble = Double.parseDouble(doubleAppend);
        }
        if (inputProof.length() > 0 && inputProof.contains(".")) {
            inputProofDouble = Double.parseDouble(inputProof);
        }
        if (inputProof.isEmpty()){
            inputProofDouble = 0.0;
        }
        System.out.println("inputProofDouble = " + inputProofDouble);
        /////////////////////////
        String inputProofCorrection = ((EditText) findViewById(R.id.editTextHydrometerCorrectionMain)).getText().toString();
        if (inputProofCorrection.length() > 0 && !inputProofCorrection.contains(".")) {
            String doubleAppend = inputProofCorrection + ".0";
            inputProofCorrDouble = Double.parseDouble(doubleAppend);
        }
        if (inputProofCorrection.length() > 0 && inputProofCorrection.startsWith(".")) {
            String appendDot = "0" + inputProofCorrection;
            inputProofCorrDouble = Double.parseDouble(appendDot);
        } else if (inputProofCorrection.length() > 0 && inputProofCorrection.contains(".")) {
            inputProofCorrDouble = Double.parseDouble(inputProofCorrection);
        }
        System.out.println("inputProofCorrDouble = " + inputProofCorrDouble);

        TextView calculatedProof = findViewById(R.id.textViewCalculatedProofMain);

        double proofFromProofing = proofing.proof(inTempDouble, inputProofDouble, inputProofCorrDouble, inputTempCorrDouble);
        if (proofFromProofing < 0) {
            calculatedProof.setText("Does not exist. Check measurements and try again.");
        } else calculatedProof.setText(String.valueOf(proofFromProofing));


//        calculatedProof.setText(String.valueOf(proofing.proof(inTempDouble, inputProofDouble, inputProofCorrDouble, inputTempCorrDouble)));
    }

    public void limitAndCalculate() {
        EditText tempField = findViewById(R.id.editTextTemperatureMain);
        InputFilterMinMax tempLimits = new InputFilterMinMax(1.0, 100.0);
        tempField.setFilters(new InputFilter[]{tempLimits});

        EditText tempCorrectionField = findViewById(R.id.editTextTemperatureCorrectionMain);
        InputFilterMinMax tempCorrLimits = new InputFilterMinMax(-1.0, 1.0);
        tempCorrectionField.setFilters(new InputFilter[]{tempCorrLimits});

        EditText proofField = findViewById(R.id.editTextHydrometerMain);
        InputFilterMinMax proofLimits = new InputFilterMinMax(1.0, 206.0);
        proofField.setFilters(new InputFilter[]{proofLimits});

        EditText proofCorrectionField = findViewById(R.id.editTextHydrometerCorrectionMain);
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
    public String userLocalTime() {

        TimeZone timeZone = TimeZone.getDefault();
        return ZonedDateTime.now(ZoneId.of(timeZone.getID()))
                .format(
                        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                .withLocale(Locale.US)
                );
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

    /**
     * Radio button listener for C/F on quick calculator.
     * Note: Temperature setting on MainActivity is not saved to user settings (user is not logged in).
     *
     * @param view
     */
    public void onMainRadioButtonClicked(View view) {
        tempField = findViewById(R.id.editTextTemperatureMain);
//        double savedC;
//        double savedF;

        boolean checked = ((RadioButton) view).isChecked();
            switch (view.getId()) {
            case R.id.radioButtonTempCMain:
                if (checked)
                    if (temperatureUnit.equals(TemperatureUnit.FAHRENHEIT) && tempField.length() > 0) {
                    temperatureUnit = TemperatureUnit.CELSIUS;

                    double getTemp = inTempDouble;
//                    double getTemp = Double.parseDouble(tempField.getText().toString());
                    double convertTemp = ((getTemp - 32) * .5556);

                    BigDecimal roundTemp = new BigDecimal(convertTemp);
                    MathContext decimalPlaces = new MathContext(4);
                    BigDecimal rounded = roundTemp.round(decimalPlaces);

                    String roundedString = String.valueOf(rounded);

                    System.out.println("rounded = " + rounded);
                    System.out.println("roundedString = " + roundedString);

                    tempField.setText(roundedString);
                    inTempDouble = Double.parseDouble(roundedString);
//                        inTempDouble = rounded.toBigInteger().doubleValue();
//                        savedC = rounded.toBigInteger().doubleValue();
                    }
                tempLimits = new InputFilterMinMax(-17.22, 37.78);
                tempField.setFilters(new InputFilter[]{tempLimits});
                break;
            case R.id.radioButtonTempFMain:
                if (checked)
                if (temperatureUnit.equals(TemperatureUnit.CELSIUS) && tempField.length() > 0) {
                    temperatureUnit = TemperatureUnit.FAHRENHEIT;

                    double getTemp = inTempDouble;
//                    double getTemp = Double.parseDouble(tempField.getText().toString());
                    double convertTemp = ((getTemp * 1.8) + 32);
                    System.out.println("convertTemp = " + convertTemp);

                    BigDecimal roundTemp = new BigDecimal(convertTemp);
                    MathContext decimalPlaces = new MathContext(4);
                    BigDecimal rounded = roundTemp.round(decimalPlaces);

                    String roundedString = String.valueOf(rounded);

                    System.out.println("rounded = " + rounded);
                    System.out.println("roundedString = " + roundedString);

                    tempField.setText(roundedString);
                    inTempDouble = Double.parseDouble(roundedString);
//                    inTempDouble = rounded.toBigInteger().doubleValue();
//                    savedF = rounded.toBigInteger().doubleValue();
                }
                tempLimits = new InputFilterMinMax(1.0, 100.0);
                tempField.setFilters(new InputFilter[]{tempLimits});
                break;
        }
    }

}
