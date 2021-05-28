package com.trueproof.trueproof.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.amplifyframework.core.Amplify;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.ActivityUtils;

import javax.inject.Inject;

public class MeasurementDetailActivity extends AppCompatActivity {

    String TAG = "TrueProof.MeasurementDetailActivity";

    @Inject
    ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_detail);


//        Intent intent = getIntent();
//        if (intent.getDoubleExtra("tempCorrection", 0.0) != 0.0 &&
//            intent.getDoubleExtra("hydroCorrection", 0.0) != 0.0){
//            String temp = String.valueOf(intent.getDoubleExtra("temp", 0.0));
//            String hydro = String.valueOf(intent.getDoubleExtra("hydro", 0.0));
//            ((TextView) findViewById(R.id.))
//        } else {
//            double temp = intent.getDoubleExtra("temp", 0.0);
//            double tempCorrection = intent.getDoubleExtra("tempCorrection", 0.0);
//
//
//        }

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        return activityUtils.onOptionsItemSelected(this, menuItem);
    }
}