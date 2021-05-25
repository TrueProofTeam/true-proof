package com.trueproof.trueproof.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.trueproof.trueproof.R;

public class SignUpConfirmationActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_confirmation);
        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    Toast.makeText(getBaseContext(),"Account Successfully made and confirmed!",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(SignUpConfirmationActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        initializeButtons();
    }

    void initializeButtons(){
        Button confirmButton = findViewById(R.id.buttonConfirmConfirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText code = findViewById(R.id.editTextConfirmationCodeConfirmation);
                if(code.getText().toString().length()==0){
                    code.setError("Please Type in your confirmation code.");
                    return;
                }
                Intent i = getIntent();
                String username = i.getStringExtra("username");
                Amplify.Auth.confirmSignUp(
                        username,
                        code.getText().toString(),
                        r->{
                            handler.sendEmptyMessage(1);
                        },
                        r->{
                            Toast.makeText(getBaseContext(),"Wrong Code, Please re-type and Trying Again",Toast.LENGTH_LONG).show();
                        }
                );
            }
        });
    }
}