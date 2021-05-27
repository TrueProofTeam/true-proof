package com.trueproof.trueproof;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.trueproof.trueproof.utils.UserSettings;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;

import io.reactivex.rxjava3.core.Completable;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AuthTest {
    @Test
    public void logIn() throws AmplifyException {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        setUpAmplify(appContext);

        CompletableFuture<String> future = new CompletableFuture<>();

        Amplify.Auth.signIn("ed@edhou.com", "aoeu1234",
                r -> {

                },
                e -> {

                });


    }

    private void setUpAmplify(Context applicationContext) throws AmplifyException {
        Amplify.addPlugin(new AWSApiPlugin());
        Amplify.addPlugin(new AWSCognitoAuthPlugin());
        //Amplify.addPlugin(new AWSDataStorePlugin());
        Amplify.configure(applicationContext);
        Log.i("MyAmplifyApp", "Initialized Amplify");
    }
}
