package com.trueproof.trueproof.utils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TestDependencyInjection {
    String helloWorld = "hello";

    @Inject
    public TestDependencyInjection() {
        // Initialize object
    }

    public String hello() {
        return helloWorld;
    }
}
