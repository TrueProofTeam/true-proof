package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.trueproof.trueproof.R;

public class LogInActivity extends AppCompatActivity {
    static final String TAG = "TrueProof.LogInActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initializeAllButton();
    }


    void initializeAllButton(){
        Button signUpButton = findViewById(R.id.buttonSignupLogin);





        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });
    }
}