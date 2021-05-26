package com.trueproof.trueproof.utils;

import com.amplifyframework.core.model.temporal.Temporal;

import org.junit.Test;

import static org.junit.Assert.*;

public class AWSDateTimeFormatterTest {
    @Test
    public void yearMonthAndDate () {
        Temporal.DateTime dateTime = new Temporal.DateTime("2021-05-26T19:18:51.232Z");
        assertEquals("2021", AWSDateTimeFormatter.ofPattern("y").format(dateTime));
        assertEquals("5", AWSDateTimeFormatter.ofPattern("M").format(dateTime));
        assertEquals("26", AWSDateTimeFormatter.ofPattern("d").format(dateTime));
    }
}