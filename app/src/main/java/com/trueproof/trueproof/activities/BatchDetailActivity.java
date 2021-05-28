package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Status;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.ActivityUtils;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.JsonConverter;
import com.trueproof.trueproof.utils.UserSettings;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BatchDetailActivity extends AppCompatActivity {
    final static String BATCH_JSON = "batch_json";
    final static String TAG = "BatchDetailActivity";

    @Inject JsonConverter jsonConverter;
    @Inject BatchRepository batchRepository;
    @Inject ActivityUtils activityUtils;

    @Inject
    UserSettings userSettings;

    Batch batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_detail);

        Log.i(TAG, "onCreate: batch detail");
       Distillery distillery = userSettings.getCachedDistillery();
        if (distillery != null)((TextView)findViewById(R.id.textViewBatchDetaildsp)).setText(distillery.getName());

        findViewById(R.id.imageButtonAddMeasurementBatchDetail).setOnClickListener(v -> goToTakeMeasurementActivity());
        Intent intent = getIntent();
        getBatchFromIntent(intent);
        initializeUpdateButton();


    }

    private void goToTakeMeasurementActivity() {
        Intent intent = new Intent(this, TakeMeasurementActivity.class);
        getBatchFromIntent(intent);
        startActivity(intent);
    }

    private void getBatchFromIntent(Intent intent) {
        String json = intent.getStringExtra(BATCH_JSON);
        if (json != null) {
            Log.i(TAG, "getBatchFromIntent: "+ json);
            batch = jsonConverter.batchFromJson(json);
            populateTextFields();

        } else {
            // TODO This error state is hopefully unreachable.
            Log.e(TAG, "No batch JSON in the intent!");
        }
    }
    private void initializeUpdateButton() {

        Button button = findViewById(R.id.buttonUpdateBatch);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type =  ((EditText)findViewById(R.id.editTextTypeBatchDetail)).getText().toString();
                    int number =  Integer.parseInt(String.valueOf(((EditText)findViewById(R.id.editTextBatchNumberBatchDetail)).getText().toString()));
                    String batchIdentifier = ((EditText)findViewById(R.id.editTextIdentifierBatchDetail)).getText().toString();
                    Batch batch1 = Batch.builder().status(Status.ACTIVE).type(type).batchNumber(number).batchIdentifier(batchIdentifier).distillery(batch.getDistillery()).id(batch.getId()).build();
                    batchRepository.updateBatch(batch1, onSuccess ->{
                        button.setText("UPDATED");
                    }, onFail->{});

                }
            });

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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return activityUtils.onOptionsItemSelected(this, menuItem);
    }
}
