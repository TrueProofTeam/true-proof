package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.trueproof.trueproof.R;

public class UserSettingsActivity extends AppCompatActivity {
    static final String TAG = "TrueProof.UserSettingsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
    }
}