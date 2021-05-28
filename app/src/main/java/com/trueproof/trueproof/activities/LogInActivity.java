package com.trueproof.trueproof.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.UserSettings;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LogInActivity extends AppCompatActivity {
    static final String TAG = "TrueProof.LogInActivity";
    Handler handler;

    @Inject
    UserSettings userSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        makeHandler();
        initializeAllButton();
    }


    void initializeAllButton(){
        Button signUpButton = findViewById(R.id.buttonSignupLogin);
        Button loginButton = findViewById(R.id.buttonLoginLogin);
        Button quickProofButton = findViewById(R.id.buttonQuickProofLogin);


        quickProofButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.editTextUsernameLogin);
                EditText password = findViewById(R.id.editTextPasswordLogin);
                if(username.getText().toString().length()==0){
                    username.setError("Please type in a username");
                    return;
                }else if(password.getText().toString().length()==0){
                    password.setError("Please type in your password!");
                    return;
                }

                Amplify.Auth.signIn(
                        username.getText().toString(),
                        password.getText().toString(),
                        r->{
                            if(r.isSignInComplete()){
                                handler.sendEmptyMessage(1);
                                userSettings.refreshCache(r2->{},r2->{});
                                Log.i(TAG, "onClick: Successful response" + r);
                            }else {
                                handler.sendEmptyMessage(2);
                                Log.i(TAG, "onClick: Successful response but inside ELSE"+r);
                            }
                        },
                        r->{
                            handler.sendEmptyMessage(2);
                            Log.i(TAG, "onClick: Error"+r);
                        }
                );
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    void makeHandler(){
        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    Toast.makeText(getBaseContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else if(msg.what==2){
                    Toast.makeText(getBaseContext(),"Login Failed, Please Try Again!",Toast.LENGTH_LONG).show();
                }
            }
        };
    }
}