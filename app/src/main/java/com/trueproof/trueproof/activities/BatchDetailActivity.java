package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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


    private void redirectToTakeMeasurement() {
        Intent redirectIntent = new Intent(this, TakeMeasurementActivity.class);
        redirectIntent.putExtra(BATCH_JSON, jsonConverter.batchToJson(batch));
        startActivity(redirectIntent);
    }

    private void populateTextFields(){
        if (batch != null){
            ((EditText) findViewById(R.id.editTextTypeBatchDetail)).setText(batch.getType());
            ((EditText) findViewById(R.id.editTextBatchNumberBatchDetail)).setText(batch.getBatchNumber());
            ((EditText) findViewById(R.id.editTextIdentifierBatchDetail)).setText(batch.getBatchIdentifier());
        }


    }

}