package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.trueproof.trueproof.R;

public class SignUpActivity extends AppCompatActivity {
    static final String TAG = "TrueProof.SignUpActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}