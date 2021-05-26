package com.trueproof.trueproof.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

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

        setupHyperlink();

    }

    private void setupHyperlink(){
        TextView ttbTable = findViewById(R.id.textViewTTBGaugingManual);
        ttbTable.setMovementMethod(LinkMovementMethod.getInstance());
        ttbTable.setLinkTextColor(Color.BLUE);

        TextView interpolationManual = findViewById(R.id.textViewInterpolationGaugingManual);
        interpolationManual.setMovementMethod(LinkMovementMethod.getInstance());
        interpolationManual.setLinkTextColor(Color.BLUE);

        TextView eCFR = findViewById(R.id.textViewGaugingECFR);
        eCFR.setMovementMethod(LinkMovementMethod.getInstance());
        eCFR.setLinkTextColor(Color.BLUE);
    }

}