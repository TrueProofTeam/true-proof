package com.trueproof.trueproof.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.TestDependencyInjection;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";

    TestDependencyInjection testDependencyInjection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate: " + testDependencyInjection.hello());
    }
}