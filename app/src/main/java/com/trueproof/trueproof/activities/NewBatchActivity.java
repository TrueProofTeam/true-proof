package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.logic.Proofing;
import com.trueproof.trueproof.utils.BatchRepository;
import com.trueproof.trueproof.utils.DistilleryRepository;
import com.trueproof.trueproof.utils.JsonConverter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewBatchActivity extends AppCompatActivity {
    static String TAG = "t.newBatch";

    @Inject
    DistilleryRepository distilleryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batch);

//        TODO retrieve distillery from intent or db
//        Distillery distillery = distilleryRepository.getDistilleryByUser();
        ((Button) findViewById(R.id.buttonCreateBatchNewBatch)).setOnClickListener(v -> {
            String batchType = ((EditText) findViewById(R.id.editTextBatchTypeNewBatch)).getText().toString();
            Integer batchNum = Integer.parseInt(((EditText) findViewById(R.id.editTextBatchNumNewBatch)).getText().toString());

            String batchIdentifier = ((EditText) findViewById(R.id.editTextBatchIdNewBatch)).getText().toString();

            Batch batch = Batch.builder().status(Status.ACTIVE).batchIdentifier(batchIdentifier).batchNumber(batchNum).distillery(distilleries.get(0)).build();

//                    .batchIdentifier(batchIdentifier).batchNumber(batchNum).type(batchType).distillery(distilleries.get(0)).status(Status.ACTIVE).build();
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
    }
}