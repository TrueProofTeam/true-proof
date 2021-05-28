package com.trueproof.trueproof.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.trueproof.trueproof.R;
import com.trueproof.trueproof.logic.Proofing;

import java.io.InputStream;

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