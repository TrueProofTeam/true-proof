package com.trueproof.trueproof.utils;

import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JsonConverter {
    public Gson gson;

    @Inject
    public JsonConverter() {
         gson = new Gson();
    }



    public String measurementToJson (Measurement measurement){
        return gson.toJson(measurement);
    }
    public String batchToJson (Batch batch){
        return gson.toJson(batch);
    }
    public String distilleryToJson (Distillery distillery){
        return gson.toJson(distillery);
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
