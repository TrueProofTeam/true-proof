package com.trueproof.trueproof.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.MeasurementRepository;

import org.jetbrains.annotations.NotNull;

public class UserSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);


    }
    public void onRadioButtonUserSettings (View view){
        boolean defaultFahrenheit = true;        // TODO refactor this to query a saved user setting based on selected preference
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonTempCUserSettings:
                if (checked)
                    // TODO display this page in C
                    break;
            case R.id.radioButtonTempFUserSettings:
                if (checked)
                    // TODO display this page in F

                    break;
        }
    }

}