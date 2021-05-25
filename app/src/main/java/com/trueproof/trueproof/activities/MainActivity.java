package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.TestDependencyInjection;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    static final String TAG = "TrueProof.MainActivity";

    @Inject
    TestDependencyInjection testDependencyInjection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeLoginButton();
        Log.i(TAG, "onCreate: " + testDependencyInjection.hello());
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.nav_settings)MainActivity.this.startActivity(new Intent(MainActivity.this, DistillerySettingsActivity.class));
        if (menuItem.getItemId() == R.id.nav_batch_list)MainActivity.this.startActivity(new Intent(MainActivity.this, BatchListActivity.class));
        if (menuItem.getItemId() == R.id.nav_settings)MainActivity.this.startActivity(new Intent(MainActivity.this, DistillerySettingsActivity.class));
        if (menuItem.getItemId() == R.id.nav_quick_calculator)MainActivity.this.startActivity(new Intent(MainActivity.this, TakeMeasurementActivity.class));
        return true;
        }

    void initializeLoginButton() {
        Button loginButton = findViewById(R.id.buttonLoginMain);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });


    }
    }
