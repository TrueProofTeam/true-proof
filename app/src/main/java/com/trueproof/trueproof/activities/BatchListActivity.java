package com.trueproof.trueproof.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.trueproof.trueproof.R;
import com.trueproof.trueproof.viewmodels.BatchListViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BatchListActivity extends AppCompatActivity {
    static final private String TAG = "BatchListActivity";
    private BatchListViewModel batchListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_list);
        batchListViewModel = new ViewModelProvider(this).get(BatchListViewModel.class);
        Log.i(TAG, "onCreate: " + batchListViewModel.test());
    }
}