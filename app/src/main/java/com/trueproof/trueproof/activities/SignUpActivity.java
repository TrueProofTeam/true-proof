package com.trueproof.trueproof.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.amplifyframework.datastore.generated.model.User;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.DistilleryRepository;
import com.trueproof.trueproof.utils.UserSettings;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpActivity extends AppCompatActivity {
    String TAG= "TrueProof.SignUpActivity";
    static boolean agreementFromUser=false;
    Handler handler;
    boolean signUpFlag = false;
    boolean distilleryAddedFlag = false;
    boolean userMadeFlag = false;

    @Inject
    UserSettings settings;

    @Inject
    DistilleryRepository distilleryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        handler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if(msg.what==1){
                    signUpFlag=true;
                }
                if(msg.what==2){
                    distilleryAddedFlag=true;
                }
                if(msg.what==3){
                    Toast.makeText(getBaseContext(),"Something went wrong! Email already in use or try again",Toast.LENGTH_LONG).show();
                }
                if(msg.what==4){
                    userMadeFlag=true;
                }
                if(signUpFlag && distilleryAddedFlag && userMadeFlag){
                    signUpFlag=false;
                    distilleryAddedFlag=false;
                    userMadeFlag=false;
                    Toast.makeText(getBaseContext(),"Account has been made! Please confirmed the account now!",Toast.LENGTH_LONG).show();
                    EditText emailConfirm = findViewById(R.id.editTextEmailConfirmationSignup);
                    Intent i = new Intent(SignUpActivity.this,SignUpConfirmationActivity.class);
                    i.putExtra("username",emailConfirm.getText().toString());
                    startActivity(i);
                    finish();
                }
            }
        };

        initializeButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(agreementFromUser){
            Button agreementButton = findViewById(R.id.userAgreementButton);
            agreementButton.setBackgroundColor(Color.BLUE);
        }
    }

    void initializeButtons(){
        Button userAgreeButton = findViewById(R.id.userAgreementButton);
        Button signUpButton = findViewById(R.id.buttonSignupSignup);

        //listener for the sign up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.editTextEmailSignup);
                EditText emailConfirm = findViewById(R.id.editTextEmailConfirmationSignup);
                EditText password = findViewById(R.id.editTextPasswordSignup);
                EditText passwordConfirm = findViewById(R.id.editTextPasswordConfirmationSignup);
                EditText dsp = findViewById(R.id.editTextDSP);

                //logic to check if the form is set up as we need it to be and force the user to change it if not
                if(!email.getText().toString().contains("@")){
                    email.setError("Please type in a proper email!");
                    return;
                }else if(!email.getText().toString().equals(emailConfirm.getText().toString())){
                    emailConfirm.setError("Your emails do not match! Please Correct This");
                    return;
                }

                if(password.getText().length()<8){
                    password.setError("Password MUST be at least 8 characters.");
                    return;
                }else if(password.getText().toString().contains(" ")){
                    password.setError("Password can NOT contains spaces");
                    return;
                }else if(!password.getText().toString().equals(passwordConfirm.getText().toString())){
                    passwordConfirm.setError("Passwords do not match!");
                    return;
                }else if(password.getText().length()==0){
                    password.setError("Please type in a password!");
                    return;
                }

                if(dsp.getText().toString().length()==0){
                    dsp.setText("");
                }

                //forces users to go into the user agreement activity and accept terms and uses
                if(!agreementFromUser){
                    Toast.makeText(getBaseContext(),"Please read the User Agreement and Accept!",Toast.LENGTH_LONG).show();
                    return;
                }

                //If we get here, the form is good to go, underneath is the distillery being made and a new user being made as well
                Distillery distillery = Distillery.builder()
                        .dspId(dsp.getText().toString())
                        .build();

                User user = User.builder()
                        .email(emailConfirm.getText().toString())
                        .defaultHydrometerCorrection(0.0)
                        .defaultTemperatureCorrection(0.0)
                        .defaultTemperatureUnit(TemperatureUnit.FAHRENHEIT)
                        .build();

                //might fix attributes, needs to made into a list so that options can use userAttributes
                Log.i(TAG, "this is the disillery id "+distillery.getId());
                List<AuthUserAttribute> attributes = new ArrayList<>();
                attributes.add(new AuthUserAttribute(AuthUserAttributeKey.custom("custom:distilleryId"),distillery.getId()));
                attributes.add(new AuthUserAttribute(AuthUserAttributeKey.custom("custom:userId"),user.getId()));

                AuthSignUpOptions options = AuthSignUpOptions.builder()
                        .userAttributes(attributes)
                        .build();

                Amplify.Auth.signUp(
                        emailConfirm.getText().toString(),
                        passwordConfirm.getText().toString(),
                        options,
                        r->{
                            handler.sendEmptyMessage(1);
                        },
                        r->{
                            handler.sendEmptyMessage(3);
                            Log.i(TAG, "sign up failed ->"+r.toString());
                        }
                );

               distilleryRepository.saveDistillery(distillery,
                       r->{
                           handler.sendEmptyMessage(2);
                           Log.i(TAG, "Success response from distillery"+r);
                           Log.i(TAG,"This is the distillery made ->>"+distillery.toString());
                       },
                       r->{
                           Log.i(TAG, "something with wrong with adding the distillery to the database -> "+r.toString());
                           handler.sendEmptyMessage(3);
                       });

                settings.addUser(user,
                        r->{
                            Log.i(TAG, "user was added to db!!! ->"+r);
                            handler.sendEmptyMessage(4);
                        },
                        r->{
                            Log.i(TAG, "failed to add users to db"+r);
                            handler.sendEmptyMessage(3);
                        });
            }

        });


        userAgreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this,UserAgreementActivity.class);
                startActivity(i);
            }
        });

    }
}