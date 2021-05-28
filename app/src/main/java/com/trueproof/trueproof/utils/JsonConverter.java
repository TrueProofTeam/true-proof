package com.trueproof.trueproof.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JsonConverter {
    public Gson gson;

    @Inject
    public JsonConverter() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Temporal.DateTime.class, new TemporalDateTimeDeserializer())
                .registerTypeAdapter(Temporal.DateTime.class, new TemporalDateTimeSerializer())
                .registerTypeAdapter(Distillery.class, new DistilleryDeserializer())
                .registerTypeAdapter(Distillery.class, new DistillerySerializer())
                .create();
    }

    public String measurementToJson(Measurement measurement) {
        return gson.toJson(measurement);
    }

    public String batchToJson(Batch batch) {
        return gson.toJson(batch);
    }

    public String distilleryToJson(Distillery distillery) {
        return gson.toJson(distillery);
    }

    public Measurement measurementFromJson(String json) {
        return gson.fromJson(json, Measurement.class);
    }

    public Batch batchFromJson(String json) {
        return gson.fromJson(json, Batch.class);
    }

    public Distillery distilleryFromJson(String json) {
        return gson.fromJson(json, Distillery.class);
    }

    private static final class TemporalDateTimeSerializer implements JsonSerializer<Temporal.DateTime> {
        @Override
        public JsonElement serialize(Temporal.DateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format());
        }
    }

    private static final class TemporalDateTimeDeserializer implements JsonDeserializer<Temporal.DateTime> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Temporal.DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return new Temporal.DateTime(json.getAsString());
        }
    }

    private static final class DistilleryDeserializer implements JsonDeserializer<Distillery> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Distillery deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject distillery = json.getAsJsonObject();
            List<String> users = null;
            JsonElement usersJson = distillery.get("users");
            if (usersJson != null && usersJson.isJsonArray()) {
                List<String> usersList = new ArrayList<>();
                distillery.get("users")
                        .getAsJsonArray()
                        .forEach(jsonElement -> usersList.add(json.getAsString()));
                users = usersList;
            }

            String id = distillery.get("id") != null ? distillery.get("id").getAsString() : null;
            String dspId = distillery.get("dspId") != null ? distillery.get("dspId").getAsString() : null;
            String name = distillery.get("name") != null ? distillery.get("name").getAsString() : null;

            return Distillery.builder()
                    .id(id)
                    .dspId(dspId)
                    .name(name)
                    .users(users)
                    .build();
        }
    }

    private static final class DistillerySerializer implements JsonSerializer<Distillery> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public JsonElement serialize(Distillery src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", src.getId());
            jsonObject.addProperty("name", src.getName());
            jsonObject.addProperty("dspId", src.getDspId());
            JsonArray users = null;
            if (src.getUsers() != null) {
                users = new JsonArray();
                src.getUsers().forEach(users::add);
            }
            jsonObject.add("users", users);
            return jsonObject;
        }
    }
}