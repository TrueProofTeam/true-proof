package com.trueproof.trueproof.utils;

import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.google.gson.Gson;

public class JsonConverter <T>{
    public Gson gson = new Gson();
    public String toJson (T object){
        return gson.toJson(object);
    }
    public Measurement measurementFromJson (String json){
         return gson.fromJson(json, Measurement.class);
    }
    public Batch batchFromJson (String json){
        return gson.fromJson(json, Batch.class);
    }
    public Distillery distilleryFromJson (String json){
        return gson.fromJson(json, Distillery.class);
    }
}
