package com.trueproof.trueproof.di;

import android.content.Context;
import android.content.res.Resources;

import com.trueproof.trueproof.logic.Proofing;

import java.io.InputStream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ProofingModule {
    @Singleton
    @Provides
    Proofing provideProofing(@ApplicationContext Context context) {
        Resources resources = context.getResources();
        InputStream inputStream = resources.openRawResource(
                resources.getIdentifier("table1",
                        "raw", context.getPackageName()));
        return new Proofing(inputStream);
    }
}
