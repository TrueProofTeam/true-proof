package com.trueproof.trueproof.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.trueproof.trueproof.R;

public class DistillerySettingsActivity extends AppCompatActivity {
    static final String TAG = "TrueProof.DistillerySettingsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distillery_settings);
    }
}