package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.Batch;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.JsonConverter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BatchDetailActivity extends AppCompatActivity {
    final static String REDIRECT_TO_TAKE_MEASUREMENT = "redirect_to_take_measurement";
    final static String BATCH_JSON = "batch_json";
    final static String TAG = "BatchDetailActivity";

    @Inject
    JsonConverter jsonConverter;

    Batch batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_detail);

        Intent intent = getIntent();

        getBatchFromIntent(intent);

        boolean redirect = intent.getBooleanExtra(REDIRECT_TO_TAKE_MEASUREMENT, false);
        if (redirect) {
            redirectToTakeMeasurement();
        }
    }

    private void getBatchFromIntent(Intent intent) {
        String json = intent.getStringExtra(BATCH_JSON);
        if (json != null) {
            batch = jsonConverter.batchFromJson(json);
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
}