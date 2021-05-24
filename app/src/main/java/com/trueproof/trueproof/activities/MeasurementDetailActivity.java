package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.trueproof.trueproof.R;

public class MeasurementDetailActivity extends AppCompatActivity {
    static final String TAG = "TrueProof.MeasurementDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_detail);
    }
}