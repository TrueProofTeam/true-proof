package com.trueproof.trueproof.models;

import com.amplifyframework.datastore.generated.model.Distillery;

public class DistilleryUtils {
    public static String toHeaderString(Distillery distillery) {
        String name = distillery.getName();
        if (name == null)
            return String.format("%s", name);
        else return "";
    }
}
