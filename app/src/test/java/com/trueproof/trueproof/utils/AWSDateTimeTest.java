package com.trueproof.trueproof.utils;

import com.amplifyframework.core.model.temporal.Temporal;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.Assert.*;

public class AWSDateTimeTest {
    static String utc = "2021-05-26T19:18:51.232Z";

    @Test
    public void ofOffsetDateTime() {
        Temporal.DateTime dateTime = new Temporal.DateTime(utc);

        OffsetDateTime odt = OffsetDateTime.parse(utc);
        assertEquals(dateTime, AWSDateTime.of(odt));
    }

    @Test
    public void ofInstant() {
        Temporal.DateTime dateTime = new Temporal.DateTime(utc);

        Instant instant = OffsetDateTime.parse(utc).toInstant();
        assertEquals(dateTime, AWSDateTime.of(instant));
    }
}