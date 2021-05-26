package com.trueproof.trueproof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.trueproof.trueproof.R;
import com.trueproof.trueproof.logic.Proofing;

import java.io.InputStream;

import static com.trueproof.trueproof.activities.SignUpActivity.agreementFromUser;

public class UserAgreementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);

//        try {
//            Resources res = getResources();
//            InputStream in_s = res.openRawResource(R.raw.termsofuse);
//            byte[] b = new byte[in_s.available()];
//            in_s.read(b);
//            ((TextView)findViewById(R.id.textView)).setText(new String(b));
//        } catch (Exception e) {
//            // e.printStackTrace();
//            ((TextView)findViewById(R.id.textView)).setText("Error.");
//        }
        // get our html content
//        String htmlAsString = getString(R.raw.termsofuse);      // used by WebView
//        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView
        // set the html content on a TextView
//        TextView textView = (TextView) findViewById(R.id.textView);
//        textView.setText(htmlAsSpanned);
//        WebView webView = (WebView) findViewById(R.id.textView);
//        webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);

        initializeButton();
    }

    void initializeButton(){
        Button agreeButton = findViewById(R.id.buttonAcceptTermsUserAgreement);

        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agreementFromUser=true;
                finish();
            }
        });

    }
}