package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.trueproof.trueproof.R;

public class References extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references);

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