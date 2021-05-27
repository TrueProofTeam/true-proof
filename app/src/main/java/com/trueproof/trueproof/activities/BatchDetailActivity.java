package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.Batch;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.JsonConverter;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BatchDetailActivity extends AppCompatActivity {
    final static String BATCH_JSON = "batch_json";
    final static String TAG = "BatchDetailActivity";

    @Inject
    JsonConverter jsonConverter;

    Batch batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_detail);

        Log.i(TAG, "onCreate: batch detail");

        findViewById(R.id.imageButtonAddMeasurementBatchDetail).setOnClickListener(v -> goToTakeMeasurementActivity());

        Intent intent = getIntent();
        getBatchFromIntent(intent);
    }

    private void goToTakeMeasurementActivity() {
        Intent intent = new Intent(this, TakeMeasurementActivity.class);
        getBatchFromIntent(intent);
        startActivity(intent);
    }

    private void getBatchFromIntent(Intent intent) {
        String json = intent.getStringExtra(BATCH_JSON);
        if (json != null) {
            batch = jsonConverter.batchFromJson(json);
            populateTextFields();
        } else {
            // TODO This error state is hopefully unreachable.
            Log.e(TAG, "No batch JSON in the intent!");
        }
    }

    private void populateTextFields(){
        if (batch != null){
            if (batch.getType() != null) ((EditText) findViewById(R.id.editTextTypeBatchDetail)).setText(batch.getType());
           if (batch.getBatchNumber() != null) ((EditText) findViewById(R.id.editTextBatchNumberBatchDetail)).setText(String.valueOf(batch.getBatchNumber()));
            if (batch.getBatchIdentifier() != null) ((EditText) findViewById(R.id.editTextIdentifierBatchDetail)).setText(batch.getBatchIdentifier());
        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.nav_settings)BatchDetailActivity.this.startActivity(new Intent(BatchDetailActivity.this, SettingsActivity.class));
        if (menuItem.getItemId() == R.id.nav_batch_list)BatchDetailActivity.this.startActivity(new Intent(BatchDetailActivity.this, BatchListActivity.class));
        if (menuItem.getItemId() == R.id.nav_quick_calculator)BatchDetailActivity.this.startActivity(new Intent(BatchDetailActivity.this, TakeMeasurementActivity.class));
        return true;
    }


}
