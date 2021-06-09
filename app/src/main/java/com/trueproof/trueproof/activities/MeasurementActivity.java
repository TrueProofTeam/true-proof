package com.trueproof.trueproof.activities;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.trueproof.trueproof.filters.InputFilterMinMax;
import com.trueproof.trueproof.viewmodels.MeasurementViewModel;

abstract class MeasurementActivity extends AppCompatActivity {
    MeasurementViewModel viewModel;
    EditText temperatureEditText;
    EditText temperatureCorrectionEditText;
    EditText hydrometerEditText;
    EditText hydrometerCorrectionEditText;
    TextView temperatureCorrectionText;
    TextView temperatureText;
    TextView trueProofText;

    abstract void saveMeasurementViews();

    void setupMeasurementActivity() {
        saveMeasurementViews();
        observeLiveData();
        setupClickListeners();
        setupHydrometerFilters();
    }

    void onInvalidTrueProofCalculation() {
        trueProofText.setText("Invalid parameters");
    }

    void onValidTrueProofCalculation(double proof) {
        trueProofText.setText(String.format("%.1f", proof));
    }

    void onMeasurementEditTextChange(Boolean bool) {
        setTextEmptyIfNull(viewModel.getHydrometer(), hydrometerEditText, -1);
        setTextEmptyIfNull(viewModel.getHydrometerCorrection(), hydrometerCorrectionEditText, -1);
        clearTemperatureFilters();
        setTextEmptyIfNull(viewModel.getTemperature(), temperatureEditText, 2);
        setTextEmptyIfNull(viewModel.getTemperatureCorrection(), temperatureCorrectionEditText, 2);
        setupTemperatureFilters(viewModel.getTemperatureUnit());
        updateTemperatureTextViews();
    }

    private void observeLiveData() {
        viewModel.getUpdateEditText().observe(this, this::onMeasurementEditTextChange);
        viewModel.getTrueProof().observe(this, proof -> {
            if (proof == null) {
                onInvalidTrueProofCalculation();
            } else {
                onValidTrueProofCalculation(proof);
            }
        });
    }

    private void setupClickListeners() {
        temperatureEditText.addTextChangedListener(
                fromOnTextChanged(viewModel::setTemperature)
        );
        temperatureCorrectionEditText.addTextChangedListener(
                fromOnTextChanged(viewModel::setTemperatureCorrection)
        );
        hydrometerEditText.addTextChangedListener(
                fromOnTextChanged(viewModel::setHydrometer)
        );
        hydrometerCorrectionEditText.addTextChangedListener(
                fromOnTextChanged(viewModel::setHydrometerCorrection)
        );
    }

    private void setTextEmptyIfNull(Double value, EditText editText, int decimalPlaces) {
        if (decimalPlaces >= 0) {
            if (value == null) editText.setText("");
            else editText.setText(String.format("%." + decimalPlaces + "f", value));
        }
    }

    private void setupHydrometerFilters() {
        hydrometerCorrectionEditText.setFilters(new InputFilter[]{
                new InputFilterMinMax(-2.0, 2.0)
        });

        hydrometerEditText.setFilters(new InputFilter[]{
                new InputFilterMinMax(0, 206)
        });
    }

    private void clearTemperatureFilters() {
        temperatureEditText.setFilters(new InputFilter[]{});
        temperatureCorrectionEditText.setFilters(new InputFilter[]{});
    }

    private void setupTemperatureFilters(TemperatureUnit unit) {
        if (unit.equals(TemperatureUnit.FAHRENHEIT)) {
            temperatureEditText.setFilters(new InputFilter[]{
                    new InputFilterMinMax(1, 100)
            });
            temperatureCorrectionEditText.setFilters(new InputFilter[]{
                    new InputFilterMinMax(-1.8, 1.8)
            });
        } else {
            temperatureEditText.setFilters(new InputFilter[]{
                    new InputFilterMinMax(-17.222222222, 37.7777777777)
            });
            temperatureCorrectionEditText.setFilters(new InputFilter[]{
                    new InputFilterMinMax(-1.0, 1.0)
            });
        }
    }

    private void updateTemperatureTextViews() {
        if (TemperatureUnit.FAHRENHEIT.equals(viewModel.getTemperatureUnit())) {
            temperatureText.setText("Temperature (°F)");
            temperatureCorrectionText.setText("Correction (°F)");
        } else {
            temperatureText.setText("Temperature (°C)");
            temperatureCorrectionText.setText("Correction (°C)");
        }
    }

    static TextWatcher fromOnTextChanged(OnTextChangeListener listener) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.changed(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    private interface OnTextChangeListener {
        void changed(String s);
    }
}
