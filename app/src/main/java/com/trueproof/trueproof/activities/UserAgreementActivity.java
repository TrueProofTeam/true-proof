package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.trueproof.trueproof.R;

import static com.trueproof.trueproof.activities.SignUpActivity.agreementFromUser;

public class UserAgreementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        initializeButton();
    }

    void initializeButton(){
        Button agreeButton = findViewById(R.id.buttonAcceptTermsUserAgreement);

        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agreementFromUser=true;
                finish();
            }
        });

    }
}