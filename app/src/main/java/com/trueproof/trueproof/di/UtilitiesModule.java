package com.trueproof.trueproof.di;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class UtilitiesModule {
    @Singleton
    @Provides
    Gson provideGson() {
        return new Gson();
    }
}
