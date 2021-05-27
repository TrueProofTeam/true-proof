package com.trueproof.trueproof.utils;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.amplifyframework.core.model.temporal.Temporal;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * Utility class to create AWS (Temporal.DateTime) objects from java Temporal objects.
 *
 * Tested to work on OffsetDateTime, ZonedDateTime, and Instant objects.
 *
 * Usage:
 *
 * Temporal.DateTime awsDateTime = AWSDateTime.of(offsetDateTime);
 *
 * Batch.builder()
 *     ...
 *     .completedAt(AWSDateTime.of(zonedDateTime))
 *     .build();
 *
 * Measurement.builder()
 *     ....
 *     .takenAt(AWSDateTime.of(instant))
 *     .build();
 */
public class AWSDateTime {
    @RequiresApi(api = Build.VERSION_CODES.O)
    static public Temporal.DateTime of(TemporalAccessor temporal) {
        Instant instant = Instant.from(temporal);
        OffsetDateTime odt = OffsetDateTime.ofInstant(instant, ZoneOffset.ofTotalSeconds(0));
        return new Temporal.DateTime(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(odt));
    }
}