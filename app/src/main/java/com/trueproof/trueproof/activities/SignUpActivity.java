package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.trueproof.trueproof.R;

public class SignUpActivity extends AppCompatActivity {
    static boolean agreementFromUser=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeButtons();
    }


    void initializeButtons(){
        Button userAgreeButton = findViewById(R.id.userAgreementButton);
        Button signUpButton = findViewById(R.id.buttonSignupSignup);


        userAgreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this,UserAgreementActivity.class);
                startActivity(i);
            }
        });

    }
}