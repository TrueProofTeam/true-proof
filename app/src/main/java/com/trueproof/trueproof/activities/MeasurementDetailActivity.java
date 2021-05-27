package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.trueproof.trueproof.R;

public class MeasurementDetailActivity extends AppCompatActivity {
    private static final String TAG = "Measurement Detail";

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
        if (menuItem.getItemId() == R.id.nav_settings)MeasurementDetailActivity.this.startActivity(new Intent(MeasurementDetailActivity.this, SettingsActivity.class));
        if (menuItem.getItemId() == R.id.nav_batch_list)MeasurementDetailActivity.this.startActivity(new Intent(MeasurementDetailActivity.this, BatchListActivity.class));
        if (menuItem.getItemId() == R.id.nav_quick_calculator)MeasurementDetailActivity.this.startActivity(new Intent(MeasurementDetailActivity.this, MainActivity.class));
        return true;
    }
}