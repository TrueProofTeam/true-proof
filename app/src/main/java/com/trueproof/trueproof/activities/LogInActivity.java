package com.trueproof.trueproof.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.trueproof.trueproof.R;

public class LogInActivity extends AppCompatActivity {
    static final String TAG = "TrueProof.LogInActivity";
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        makeHandler();
        initializeAllButton();
        modifyActionbar();
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
                            }else {
                                handler.sendEmptyMessage(2);
                            }
                        },
                        r->{
                            handler.sendEmptyMessage(2);
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
    private void modifyActionbar () {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle("Login");
    }
}