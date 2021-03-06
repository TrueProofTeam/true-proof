package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Status;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.models.DistilleryUtils;
import com.trueproof.trueproof.utils.ActivityUtils;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.DistilleryRepository;
import com.trueproof.trueproof.utils.JsonConverter;
import com.trueproof.trueproof.utils.UserSettings;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewBatchActivity extends AppCompatActivity {
    final static String REDIRECT_TO_BATCH_DETAIL_TO_TAKE_MEASUREMENT = "redirect_to_take_measurement";
    static String TAG = "t.newBatch";
    @Inject
    DistilleryRepository distilleryRepository;
    @Inject
    BatchRepository batchRepository;
    @Inject
    UserSettings userSettings;
    @Inject
    JsonConverter jsonConverter;
    @Inject
    ActivityUtils activityUtils;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            batchIdentifierOnChange();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batch);

        Distillery distillery = userSettings.getCachedDistillery();
        TextView distilleryName = findViewById(R.id.textViewNewBatchdsp);
        distilleryName.setText(DistilleryUtils.toHeaderString(distillery));

        findViewById(R.id.buttonCreateBatchNewBatch).setOnClickListener(v -> {

            String batchType = ((EditText) findViewById(R.id.editTextBatchTypeNewBatch)).getText().toString();
            Integer batchNum = Integer.parseInt(((EditText) findViewById(R.id.editTextBatchNumNewBatch)).getText().toString());
            String batchIdentifier = ((EditText) findViewById(R.id.editTextBatchIdNewBatch)).getText().toString();

            Batch batch = Batch.builder()
                    .status(Status.ACTIVE)
                    .type(batchType)
                    .batchIdentifier(batchIdentifier)
                    .batchNumber(batchNum)
                    .distillery(userSettings.getCachedDistillery())
                    .build();

            batchRepository.saveBatch(batch, onSuccess -> {
                        Intent data = new Intent();
                        data.putExtra(BatchDetailActivity.BATCH_JSON, jsonConverter.batchToJson(batch));
                        setResult(BatchListActivity.REDIRECT_TO_BATCH_DETAIL_TO_TAKE_MEASUREMENT, data);
                        finish();
                    }, onFail -> {
                        Log.i(TAG, "onFail: " + onFail.toString());
                    }
            );
        });

        EditText batchTypeEditText = findViewById(R.id.editTextBatchTypeNewBatch);
        EditText batchNumEditText = findViewById(R.id.editTextBatchNumNewBatch);
        batchNumEditText.addTextChangedListener(textWatcher);
        batchTypeEditText.addTextChangedListener(textWatcher);
    }

    private void batchIdentifierOnChange() {
        Distillery distillery = userSettings.getCachedDistillery();

        if (distillery.getDspId() != null && ((EditText) findViewById(R.id.editTextBatchTypeNewBatch)).getText() != null
                && ((EditText) findViewById(R.id.editTextBatchNumNewBatch)).getText() != null) {
            String batchIdentifier = distillery.getDspId() + "-" + ((EditText) findViewById(R.id.editTextBatchTypeNewBatch)).getText().toString() + "-" +
                    ((EditText) findViewById(R.id.editTextBatchNumNewBatch)).getText().toString();
            ((EditText) findViewById(R.id.editTextBatchIdNewBatch)).setText(batchIdentifier);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return activityUtils.onOptionsItemSelected(this, menuItem);
    }
}