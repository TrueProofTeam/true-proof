package com.trueproof.trueproof.models;

import com.amplifyframework.datastore.generated.model.Distillery;

public class DistilleryUtils {
    public static String toHeaderString(Distillery distillery) {
        String name = distillery.getName();
        String dsp = distillery.getDspId();
        if (name == null || name.isEmpty()) {
            if (dsp != null && !dsp.isEmpty()) return dsp;
        } else {
            return name;
        }
        return "Your Distillery";
    }
}
