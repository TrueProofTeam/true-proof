package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Status;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.adapters.MeasurementListAdapter;
import com.trueproof.trueproof.models.DistilleryUtils;
import com.trueproof.trueproof.utils.ActivityUtils;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.JsonConverter;
import com.trueproof.trueproof.utils.UserSettings;
import com.trueproof.trueproof.viewmodels.BatchDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@RequiresApi(api = Build.VERSION_CODES.O)
public class BatchDetailActivity extends AppCompatActivity {
    final static String BATCH_JSON = "batch_json";
    final static String TAG = "BatchDetailActivity";

    @Inject
    JsonConverter jsonConverter;
    @Inject
    BatchRepository batchRepository;
    @Inject
    ActivityUtils activityUtils;
    @Inject
    UserSettings userSettings;

    private EditText editTextType;
    private EditText editTextBatchNumber;
    private EditText editTextIdentifier;
    private Spinner spinner;
    private MeasurementListAdapter measurementListAdapter;
    private BatchDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_detail);
        viewModel = new ViewModelProvider(this).get(BatchDetailViewModel.class);

        findViews();

        Log.i(TAG, "onCreate: batch detail");

        Distillery distillery = userSettings.getCachedDistillery();
        Log.i(TAG, "onCreate: distillery " + distillery);
        TextView distilleryName = findViewById(R.id.textViewBatchDetaildsp);
        distilleryName.setText(DistilleryUtils.toHeaderString(distillery));

        findViewById(R.id.imageButtonAddMeasurementBatchDetail).setOnClickListener(v -> goToTakeMeasurementActivity());

        Intent intent = getIntent();
        getBatchFromIntent(intent);
        initializeUpdateButton();
        observeLiveData();
        setUpMeasurementList();
        setUpSpinner();
    }

    private void setUpSpinner() {
        ArrayAdapter<Status> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new Status[] {Status.ACTIVE, Status.COMPLETE}
                );
        spinner.setAdapter(adapter);
    }

    private void observeLiveData() {
        viewModel.getBatch().observe(this, batch -> populateTextFields(batch));
        viewModel.getUpdatedLiveData().observe(this, success -> {
                    if (success == true)
                        Toast.makeText(this, "Batch updated!", Toast.LENGTH_LONG).show();
                    if (success == false)
                        Toast.makeText(this, "Error updating batch. Check your network connection or try again.", Toast.LENGTH_LONG).show();
                }
        );
    }

    private void findViews() {
        editTextType = findViewById(R.id.editTextTypeBatchDetail);
        editTextBatchNumber = findViewById(R.id.editTextBatchNumberBatchDetail);
        editTextIdentifier = findViewById(R.id.editTextIdentifierBatchDetail);
        spinner = findViewById(R.id.spinnerStatusBatchDetail);
    }

    private void setUpMeasurementList() {
        RecyclerView measurementList = findViewById(R.id.recyclerViewMeasurementsBatchDetail);
        measurementList.setLayoutManager(new LinearLayoutManager(this));
        measurementListAdapter = new MeasurementListAdapter(this::goToMeasurementDetail);

        final List<Measurement> l = viewModel.getMeasurements().getValue();
        measurementListAdapter.submitList(l == null ? new ArrayList<Measurement>() : l);

        measurementList.setAdapter(measurementListAdapter);
        viewModel.getMeasurements().observe(this,
                measurement -> measurementListAdapter.submitList(measurement));
    }

    private void goToMeasurementDetail(Measurement measurement) {
        Intent intent = new Intent(this, TakeMeasurementActivity.class);
        String measurementJson = jsonConverter.measurementToJson(measurement);
        intent.putExtra(MeasurementDetailActivity.MEASUREMENT_JSON, measurementJson);
        startActivity(intent);
    }

    private void goToTakeMeasurementActivity() {
        Intent intent = new Intent(this, TakeMeasurementActivity.class);
        String batchJson = jsonConverter.batchToJson(viewModel.getBatch().getValue());
        intent.putExtra(TakeMeasurementActivity.BATCH_JSON, batchJson);
        startActivity(intent);
    }

    private void getBatchFromIntent(Intent intent) {
        String json = intent.getStringExtra(BATCH_JSON);
        if (json != null) {
            Log.i(TAG, "getBatchFromIntent: " + json);
            viewModel.setBatchFromJson(json);
        } else {
            // TODO This error state is hopefully unreachable.
            Log.e(TAG, "No batch JSON in the intent!");
        }
    }

    private void initializeUpdateButton() {
        Button button = findViewById(R.id.buttonUpdateBatch);
        button.setOnClickListener(v -> onClickUpdateButton());
    }

    private void onClickUpdateButton() {
        String type = editTextType.getText().toString();
        int number = Integer.parseInt(editTextBatchNumber.getText().toString());
        String batchIdentifier = editTextIdentifier.getText().toString();
        Status status = (Status) spinner.getSelectedItem();

        Batch newBatch = viewModel.getBatch().getValue().copyOfBuilder()
                .type(type)
                .batchNumber(number)
                .batchIdentifier(batchIdentifier)
                .status(status)
                .build();
        viewModel.updateBatch(newBatch);
    }

    private void populateTextFields(Batch batch) {
        if (batch != null) {
            if (batch.getType() != null) editTextType.setText(batch.getType());
            if (batch.getBatchNumber() != null)
                editTextBatchNumber.setText(String.format("%d", batch.getBatchNumber()));
            if (batch.getBatchIdentifier() != null)
                editTextIdentifier.setText(batch.getBatchIdentifier());
            Status status = batch.getStatus();
            for (int i = 0; i < spinner.getCount(); i++) {
                if (spinner.getItemAtPosition(i).equals(status)) spinner.setSelection(i);
            }
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
