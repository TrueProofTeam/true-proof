package com.trueproof.trueproof.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.amplifyframework.datastore.generated.model.Status;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.MeasurementRepository;

import org.jetbrains.annotations.NotNull;

public class DistillerySettingsActivity extends AppCompatActivity {

    static String TAG = "t.distillerySettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distillery_settings);




    }
}