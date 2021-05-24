package com.trueproof.trueproof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.trueproof.trueproof.R;
import com.trueproof.trueproof.utils.TestDependencyInjection;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    static final String TAG = "TrueProof.MainActivity";

    TestDependencyInjection testDependencyInjection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeLoginButton();
        Log.i(TAG, "onCreate: " + testDependencyInjection.hello());
    }

    //This will initialize the button on click listener with the intent to head into the login acitvity
    void initializeLoginButton() {
        Button loginButton = findViewById(R.id.buttonLoginMain);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });


    }
}