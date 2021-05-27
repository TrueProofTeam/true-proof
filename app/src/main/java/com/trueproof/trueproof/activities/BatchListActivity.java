package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Batch;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.adapters.ActiveBatchListAdapter;
import com.trueproof.trueproof.adapters.BatchListAdapter;
import com.trueproof.trueproof.models.BatchUtils;
import com.trueproof.trueproof.utils.JsonConverter;
import com.trueproof.trueproof.viewmodels.BatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BatchListActivity extends AppCompatActivity {
    static final int REDIRECT_TO_BATCH_DETAIL_TO_TAKE_MEASUREMENT = 1;
    static final private String TAG = "BatchListActivity";
    ActivityResultLauncher<Intent> newBatchLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            this::onActivityResult
    );
    @Inject
    JsonConverter jsonConverter;
    private BatchListViewModel viewModel;
    private BatchListAdapter batchListAdapter;
    private ActiveBatchListAdapter activeBatchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_list);
        viewModel = new ViewModelProvider(this).get(BatchListViewModel.class);

        setUpAllBatchList();
        setUpActiveBatchList();

        findViewById(R.id.imageButtonNewBatchBatchList).setOnClickListener(
                v -> goToNewBatchActivity()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.update();
    }

    private void setUpAllBatchList() {
        RecyclerView allBatchList = findViewById(R.id.recyclerViewBatchList);
        allBatchList.setLayoutManager(new LinearLayoutManager(this));
        batchListAdapter = new BatchListAdapter(batch -> {
            // TODO: Go to the batch detail
            Log.i(TAG, "BatchList: clicked on batch " + BatchUtils.batchToString(batch));
            goToBatchDetail(batch);
        });

        final List<Batch> list = viewModel.getBatchList().getValue();
        if (list != null) batchListAdapter.submitList(list);
        else batchListAdapter.submitList(new ArrayList<Batch>());

        allBatchList.setLayoutManager(new LinearLayoutManager(this));
        allBatchList.setAdapter(batchListAdapter);
        viewModel.getBatchList().observe(this,
                batches -> batchListAdapter.submitList(batches));
    }

    private void setUpActiveBatchList() {
        RecyclerView activeBatchList = findViewById(R.id.recyclerViewActiveBatchList);
        activeBatchList.setLayoutManager(new LinearLayoutManager(this));
        activeBatchListAdapter = new ActiveBatchListAdapter(batch -> {
            Log.i(TAG, "ActiveBatchList: clicked on batch " + BatchUtils.batchToString(batch));
            goToBatchDetail(batch);
        });
        final List<Batch> list = viewModel.getActiveBatchList().getValue();
        if (list != null) activeBatchListAdapter.submitList(list);
        else activeBatchListAdapter.submitList(new ArrayList<Batch>());

        activeBatchList.setAdapter(activeBatchListAdapter);
        viewModel.getActiveBatchList().observe(this, batches ->
                activeBatchListAdapter.submitList(batches));
    }

    private void goToBatchDetail(Batch batch) {
        Intent intent = new Intent(this, BatchDetailActivity.class);
        intent.putExtra(BatchDetailActivity.BATCH_JSON, jsonConverter.batchToJson(batch));
        startActivity(intent);
    }

    private void goToNewBatchActivity() {
        Intent intent = new Intent(this, NewBatchActivity.class);
        newBatchLauncher.launch(intent);
    }

    private void onActivityResult(ActivityResult activityResult) {
        if (activityResult.getResultCode() == REDIRECT_TO_BATCH_DETAIL_TO_TAKE_MEASUREMENT) {
            String json = activityResult.getData().getStringExtra(BatchDetailActivity.BATCH_JSON);

            Intent batchDetailIntent = new Intent(this, BatchDetailActivity.class);
            batchDetailIntent.putExtra(BatchDetailActivity.BATCH_JSON, json);

            Intent takeMeasurementIntent = new Intent(this, TakeMeasurementActivity.class);
            takeMeasurementIntent.putExtra(BatchDetailActivity.BATCH_JSON, json);

            startActivities(new Intent[]{
                    batchDetailIntent,
                    takeMeasurementIntent
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.nav_settings)BatchListActivity.this.startActivity(new Intent(BatchListActivity.this, SettingsActivity.class));
        if (menuItem.getItemId() == R.id.nav_batch_list)BatchListActivity.this.startActivity(new Intent(BatchListActivity.this, BatchListActivity.class));
        if (menuItem.getItemId() == R.id.nav_quick_calculator)BatchListActivity.this.startActivity(new Intent(BatchListActivity.this, TakeMeasurementActivity.class));
        return true;
    }
}