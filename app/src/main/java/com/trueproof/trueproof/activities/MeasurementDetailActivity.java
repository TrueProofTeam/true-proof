package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.trueproof.trueproof.R;

public class MeasurementDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_detail);
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
        if (menuItem.getItemId() == R.id.nav_quick_calculator)MeasurementDetailActivity.this.startActivity(new Intent(MeasurementDetailActivity.this, TakeMeasurementActivity.class));
        return true;
    }
}