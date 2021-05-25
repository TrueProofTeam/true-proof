package com.trueproof.trueproof.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Batch;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.adapters.ActiveBatchListAdapter;
import com.trueproof.trueproof.adapters.BatchListAdapter;
import com.trueproof.trueproof.models.BatchUtils;
import com.trueproof.trueproof.viewmodels.BatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BatchListActivity extends AppCompatActivity {
    static final private String TAG = "BatchListActivity";
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
    }

    private void setUpAllBatchList() {
        RecyclerView allBatchList = findViewById(R.id.recyclerViewBatchList);
        batchListAdapter = new BatchListAdapter(batch -> {
            // TODO: Go to the batch detail
            Log.i(TAG, "BatchList: clicked on batch " + BatchUtils.batchToString(batch));
            Toast.makeText(this,
                    "Clicked on batch " + BatchUtils.batchToString(batch),
                    Toast.LENGTH_LONG).show();
        });
        final List<Batch> list = viewModel.getBatchList().getValue();
        if (list != null) batchListAdapter.submitList(list);
        else batchListAdapter.submitList(new ArrayList<Batch>());

        viewModel.getBatchList().observe(this, new Observer<List<Batch>>() {
            @Override
            public void onChanged(List<Batch> batches) {
                batchListAdapter.submitList(batches);
            }
        });
    }

    private void setUpActiveBatchList() {
        RecyclerView activeBatchList = findViewById(R.id.recyclerViewActiveBatchList);
        activeBatchListAdapter = new ActiveBatchListAdapter(batch -> {
            // TODO: Go to the batch detail
            Log.i(TAG, "ActiveBatchList: clicked on batch " + BatchUtils.batchToString(batch));
            Toast.makeText(this,
                    "Clicked on batch " + BatchUtils.batchToString(batch),
                    Toast.LENGTH_LONG).show();
        });
        final List<Batch> list = viewModel.getActiveBatchList().getValue();
        if (list != null) activeBatchListAdapter.submitList(list);
        else activeBatchListAdapter.submitList(new ArrayList<Batch>());

        viewModel.getActiveBatchList().observe(this, new Observer<List<Batch>>() {
            @Override
            public void onChanged(List<Batch> batches) {
                activeBatchListAdapter.submitList(batches);
            }
        });
    }
}