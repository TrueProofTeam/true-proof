package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Batch;
import com.trueproof.trueproof.R;

public class TakeMeasurementActivity extends AppCompatActivity {

    static String TAG = "t.takeMeasurement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_measurement);

        ((Button) findViewById(R.id.buttonCreateBatchNewBatch)).setOnClickListener(v -> {
            String batchType = ((EditText) findViewById(R.id.editTextBatchTypeNewBatch)).getText().toString();
            Integer batchNum = Integer.parseInt(((EditText) findViewById(R.id.editTextBatchNumNewBatch)).getText().toString());
            String batchId = ((EditText) findViewById(R.id.editTextBatchIdNewBatch)).getText().toString();
            Batch batch = Batch.builder()
                    .batchIdentifier(batchId).batchNumber(batchNum).type(batchType).build();
            Amplify.API.mutate(ModelMutation.create(batch),
                    response ->{
                        Log.i(TAG, "oncreate success");
                    },
                    response -> {
                        Log.i(TAG, "onCreate: fail");

                    });

            Toast.makeText(this, "Started batch " + batchId, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,TakeMeasurementActivity.class));
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean defaultFahrenheit = true;        // TODO refactor this to query a saved user setting based on selected preference
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonTempCTakeMeasurement:
                if (checked)
                    // TODO display this page in C
                    break;
            case R.id.radioButtonTempFTakeMeasurement:
                if (checked)
                    // TODO display this page in F

                    break;
        }
    }


}