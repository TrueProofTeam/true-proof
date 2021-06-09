package com.trueproof.trueproof.viewmodels;

import com.trueproof.trueproof.logic.Proofing;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainActivityViewModel extends MeasurementViewModel {
    private static final String TAG = "MainActivity/VM/";

    @Inject
    MainActivityViewModel(Proofing proofing) {
        super();
        this.proofing = proofing;
    }
}
