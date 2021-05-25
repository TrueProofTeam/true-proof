package com.trueproof.trueproof.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class UserSettings {
    final static String SHARED_PREFERENCES_NAME = "trueproof.trueproof";
    private SharedPreferences sharedPreferences;

    @Inject
    UserSettings(@ApplicationContext Context context, UserRepository userRepository) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, context.MODE_PRIVATE);
    }

    public void setUserName() {
    }

    public void getUserName() {
    }

    public void getTemperature() {
    }
}
