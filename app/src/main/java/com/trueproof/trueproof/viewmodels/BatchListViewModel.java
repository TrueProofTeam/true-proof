package com.trueproof.trueproof.viewmodels;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BatchListViewModel extends ViewModel {

    @Inject
    BatchListViewModel() {

    }

    public String test() {
        return "hello from ViewModel";
    }
}
