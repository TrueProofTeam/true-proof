package com.trueproof.trueproof.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JsonConverter {
    public Gson gson;

    @Inject
    public JsonConverter() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Temporal.DateTime.class, new TemporalDateTimeDeserializer())
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

    private static final class TemporalDateTimeDeserializer implements JsonDeserializer<Temporal.DateTime> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Temporal.DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonObject offsetDateTime = jsonObject.get("offsetDateTime").getAsJsonObject();
            JsonObject dateTime = offsetDateTime.get("dateTime").getAsJsonObject();
            int offsetTotalSeconds = offsetDateTime.get("offset").getAsJsonObject()
                    .get("totalSeconds").getAsInt();

            JsonObject date = dateTime.get("date").getAsJsonObject();
            JsonObject time = dateTime.get("time").getAsJsonObject();

            OffsetDateTime odt = OffsetDateTime.of(
                    date.get("year").getAsInt(),
                    date.get("month").getAsInt(),
                    date.get("day").getAsInt(),
                    time.get("hour").getAsInt(),
                    time.get("minute").getAsInt(),
                    time.get("second").getAsInt(),
                    time.get("nano").getAsInt(),
                    ZoneOffset.ofTotalSeconds(offsetTotalSeconds)
            );

            return new Temporal.DateTime(odt.toString());
        }
    }
}