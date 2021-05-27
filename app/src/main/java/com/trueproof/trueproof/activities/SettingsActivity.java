package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.amplifyframework.datastore.generated.model.User;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.UserSettings;

import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsActivity extends AppCompatActivity {
    Handler handler;
    static String TAG = "t.distillerySettings";
    Distillery usersDistillery;
    User user;

    @Inject
    UserSettings userSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case (1):
                        distillerySetUp();
                        break;
                    case (2):
                        userSetUp();
                        break;
                }
            }
        };
        distilleryRequest();
        userRequest();


    }
    void distilleryRequest(){
        userSettings.getDistillery(
                r->{
                    usersDistillery=r;
                    handler.sendEmptyMessage(1);
                },
                r->{}
        );
    }





    void userRequest(){
        userSettings.getUserSettings(
                r->{
                    user=r;
                    handler.sendEmptyMessage(2);
                },
                r->{}
        );
    }

    void  distillerySetUp(){
        EditText dspNum = findViewById(R.id.editTextDSPDistillerySettings);
        EditText distilleryName =  findViewById(R.id.editTextDistilleryNameDistillerySettings);
        TextView distilleryTitle = findViewById(R.id.textViewDSPTitleDistillerySettings);
        dspNum.setText(usersDistillery.getDspId());
        distilleryName.setText(usersDistillery.getName());
        if(usersDistillery.getName()==null){
            distilleryTitle.setText(R.string.noDistilleryNameText);
        }
    }

    void userSetUp(){
        RadioGroup radioGroup = findViewById(R.id.radioGroup2);
        EditText hydroText = findViewById(R.id.editTextDefaultHydroCorrectionUserSettings6);
        EditText thermText = findViewById(R.id.editTextDefaultTempCorrectionUserSettings6);

        if(user.getDefaultTemperatureUnit()== TemperatureUnit.CELSIUS){
            radioGroup.check(R.id.radioButtonTempCUserSettings);
        }else{
            radioGroup.check(R.id.radioButtonTempFUserSettings);
        }
        Log.i(TAG, "Hydro Correction From User ->>>" +user.getDefaultHydrometerCorrection());
        Log.i(TAG, "Thermo Correction From User ->>>"+ user.getDefaultTemperatureCorrection());
        hydroText.setText(String.format(Locale.getDefault(),"%.2f",user.getDefaultHydrometerCorrection()));
        thermText.setText(String.format(Locale.getDefault(),"%.2f",user.getDefaultTemperatureCorrection()));

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
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.nav_settings)SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
        if (menuItem.getItemId() == R.id.nav_batch_list)SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, BatchListActivity.class));
        if (menuItem.getItemId() == R.id.nav_quick_calculator)SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        return true;
    }
}