package com.trueproof.trueproof.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.amplifyframework.datastore.generated.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class UserSettings {
    final static String SHARED_PREFERENCES_NAME = "trueproof.trueproof";
    static String EXAMPLE_EMAIL = "me@you.com";

    private SharedPreferences sharedPreferences;

    @Inject
    UserSettings(@ApplicationContext Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, context.MODE_PRIVATE);
    }

    public String getEmail() {
        return EXAMPLE_EMAIL;
    }

    public Distillery getDistillery() {
        List<String> emails = new ArrayList<>();
        emails.add(EXAMPLE_EMAIL);
        return Distillery.builder()
                .dspId("WA-EXAMPLE")
                .name("Olde Tyme Whiskey Distillery")
                .users(emails)
                .build();
    }

    public void getDistillery(Consumer<Distillery> success, Consumer<Exception> fail) {
        // TODO
    }

    public Double getDefaultTemperatureCorrection() {
        return 0.5;
    }

    public Double getDefaultHydrometerCorrection() {
        return -0.2;
    }
}
