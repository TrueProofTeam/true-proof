package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.trueproof.trueproof.R;

public class UserAgreementActivity extends AppCompatActivity {
    static final String TAG = "TrueProof.UserAgreementActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
    }
}