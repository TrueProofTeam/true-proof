package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.trueproof.trueproof.R;

public class TakeMeasurementActivity extends AppCompatActivity {
    static final String TAG = "TrueProof.TakeMeasurementActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_measurement);
    }
}