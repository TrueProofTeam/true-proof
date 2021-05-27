package com.trueproof.trueproof.utils;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.amplifyframework.datastore.generated.model.Status;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonConverterTest {
    JsonConverter jsonConverter = new JsonConverter();

    static String uuid = "bcb032e2-d78b-4db1-bdea-f7248530f86d";
    static String utcTime = "2021-05-26T19:18:51.232Z";
    static String utcTime2 = "2021-12-28T01:12:48.885Z";
    static String utcTime3 = "2022-01-01T23:19:23.001Z";

    @Test
    public void batchConvert() {
        Batch batch = Batch.builder()
                .status(Status.ACTIVE)
                .distillery(Distillery.justId(uuid))
                .batchIdentifier("batchIdentifier")
                .batchNumber(1)
                .completedAt(new Temporal.DateTime(utcTime))
                .updatedAt(new Temporal.DateTime(utcTime))
                .type("whiskey")
                .build();
        String json = jsonConverter.batchToJson(batch);
        Batch deserializedBatch = jsonConverter.batchFromJson(json);
        assertEquals(batch, deserializedBatch);
    }

    @Test
    public void measuremnetConvert() {
        Measurement measurement = Measurement.builder()
                .trueProof(99.0)
                .temperature(99.0)
                .hydrometer(99.0)
                .temperatureCorrection(99.0)
                .hydrometerCorrection(99.0)
                .batch(Batch.justId(uuid))
                .createdAt(new Temporal.DateTime(utcTime))
                .takenAt(new Temporal.DateTime(utcTime2))
                .updatedAt(new Temporal.DateTime(utcTime3))
                .note("Note")
                .flag(false)
                .build();
        String json = jsonConverter.measurementToJson(measurement);
        Measurement deserializedMeasurement = jsonConverter.measurementFromJson(json);
        assertEquals(measurement, deserializedMeasurement);
    }
}