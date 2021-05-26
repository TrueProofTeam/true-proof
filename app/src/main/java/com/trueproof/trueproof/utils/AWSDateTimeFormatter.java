package com.trueproof.trueproof.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.amplifyframework.core.model.temporal.Temporal;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * Formats the DateTime object from AWS into the user's local time into a specific format.
 *
 * Usage:
 * AWSDateTimeFormatter.ofPattern("y-M-d h:m:s").format(awsDateTime);
 *
 * Or:
 * AWSDateTimeFormatter formatter = AWSDateTimeFormatter.ofPattern("y-M-d h:m:s");
 * formatter.format(measurement.getCreatedAt());
 *
 * Patterns:
 * https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class AWSDateTimeFormatter {
    private TimeZone defaultTimeZone = TimeZone.getDefault();
    private DateTimeFormatter dateTimeFormatter;

    AWSDateTimeFormatter(String pattern) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * Creates an AWSDateTimeFormatter from a given pattern.
     * @param pattern: A string that determines the way DateTimes will be formatted.
     *               see https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     * @return An AWSDateTimeFormatter that uses the given pattern to format AWS DateTime objects.
     */
    static public AWSDateTimeFormatter ofPattern(String pattern) {
        return new AWSDateTimeFormatter(pattern);
    }

    /**
     * Formats the AWS/amplify DateTime object into a string.
     * @param awsDateTime
     * @return A String representation of the DateTime that follows a pattern.
     */
    public String format(Temporal.DateTime awsDateTime) {
        ZonedDateTime zdt = OffsetDateTime.
                parse(awsDateTime.format())
                .atZoneSameInstant(defaultTimeZone.toZoneId());
        return dateTimeFormatter.format(zdt);
    }
}
