package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.amplifyframework.datastore.generated.model.User;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.DistilleryRepository;
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
    TemperatureUnit temperatureUnit;
    boolean userSaved = false;
    boolean distillerySaved=false;

    @Inject
    UserSettings userSettings;

    @Inject
    DistilleryRepository distilleryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case 1:
                        distillerySetUp();
                        break;
                    case 2:
                        userSetUp();
                        temperatureUnit=user.getDefaultTemperatureUnit();
                        break;
                    case 3:
                        userSaved=true;
                        break;
                    case 4:
                        distillerySaved=true;
                        break;
                }
                if(userSaved && distillerySaved){
                    Toast.makeText(getBaseContext(),"Your settings have been updated!",Toast.LENGTH_LONG).show();
                    userSaved=false;
                    distillerySaved=false;
                }
            }
        };
        distilleryRequest();
        userRequest();
        initializeButtons();


    }
    void distilleryRequest(){
        userSettings.getDistillery(
                r->{
                    usersDistillery=r;
                    handler.sendEmptyMessage(1);
                    Log.i(TAG, "distilleryRequest: success ->"+r);
                },
                r->{
                    Log.i(TAG, "distilleryRequest: failed ->"+r);
                }
        );
    }





    void userRequest(){
        userSettings.getUserSettings(
                r->{
                    SettingsActivity.this.user=r;
                    handler.sendEmptyMessage(2);
                    Log.i(TAG, "request user settings -->"+user);
                },
                r->{
                    Log.e(TAG, "userRequest: ERROR", r);
                }
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
        }else {
            distilleryTitle.setText(usersDistillery.getName());
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

    void initializeButtons(){
        Button submit = findViewById(R.id.buttonSubmitDistillerySettings);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This is information for the new distillery model
                String dspNumberInput = ((EditText) findViewById(R.id.editTextDSPDistillerySettings)).getText().toString();
                String dspNameInput = ((EditText) findViewById(R.id.editTextDistilleryNameDistillerySettings)).getText().toString();

                //This is information for the new user model
                double hydroCorrectionInput = Double.parseDouble(((EditText) findViewById(R.id.editTextDefaultHydroCorrectionUserSettings6)).getText().toString());
                double thermoCorrectionInput = Double.parseDouble(((EditText) findViewById(R.id.editTextDefaultTempCorrectionUserSettings6)).getText().toString());

                Distillery newDistillery = Distillery.builder()
                        .id(usersDistillery.getId())
                        .name(dspNameInput)
                        .dspId(dspNumberInput)
                        .users(usersDistillery.getUsers())
                        .build();
                User newUser = User.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .defaultHydrometerCorrection(hydroCorrectionInput)
                        .defaultTemperatureCorrection(thermoCorrectionInput)
                        .defaultTemperatureUnit(temperatureUnit)
                        .build();
                Log.i(TAG, "This is the new distillery!!! -> "+newDistillery.toString());
                Log.i(TAG, "Old distillery ->"+usersDistillery.toString());
                Log.i(TAG, "This is the new user Settings!!! ->"+newUser.toString());
                Log.d(TAG, "Old user settings "+user.toString());


                userSettings.saveUserSettings(newUser,
                        r->{
                            Log.i(TAG, "user saved!!!!! hazah ->"+r.toString());
                            handler.sendEmptyMessage(3);
                            //toast and leave em here
                        },
                        r->{
                            Log.i(TAG, "user failed to save find out why here ->> "+r.toString());
                        }
                );

                distilleryRepository.updateDistillery(newDistillery,
                        r->{
                            Log.i(TAG, "SAVED THE NEW Distillery!! ->"+r.toString());
                            handler.sendEmptyMessage(4);
                        },
                        r->{
                            Log.i(TAG, "failed to save distillery ->"+r.toString());
                        }
                );


            }
        });
    }

    /**
     * call back function for radio group in settings activity
     * Note:Method should be kept public so that the activity radio group listener can access this callback function
     * @param view      The view model or in this case the radio group with that the listener is attached to
     *
     */
    public void onRadioButtonUserSettings (View view){

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonTempCUserSettings:
                if (checked){
                    temperatureUnit=TemperatureUnit.CELSIUS;
                    Log.i(TAG, "onRadioButtonUserSettings: ->"+temperatureUnit);
                }
                break;
            case R.id.radioButtonTempFUserSettings:
                if (checked){
                    temperatureUnit=TemperatureUnit.FAHRENHEIT;
                    Log.i(TAG, "onRadioButtonUserSettings: ->"+temperatureUnit);
                }
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
        if (menuItem.getItemId() == R.id.nav_log_out){
            Amplify.Auth.signOut(
                    ()->{
                        Log.i(TAG,"Success Logout!");
                    },
                    r->{});
            SettingsActivity.this.startActivity(new Intent( SettingsActivity.this,MainActivity.class));
            finish();
        }
        return true;
    }
    private void modifyActionbar () {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle("Settings");
    }
}

