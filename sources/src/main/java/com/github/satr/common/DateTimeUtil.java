package com.github.satr.common;
// Copyright © 2019, github.com/satr, MIT License

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final ZoneId utcZoneId = ZoneId.of("UTC");

    public static String toIsoString(OffsetDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static OffsetDateTime toUtc(OffsetDateTime dateTime) {
        return dateTime.atZoneSameInstant(utcZoneId).toOffsetDateTime();
    }

    public static OffsetDateTime utcNow() {
        return OffsetDateTime.now(utcZoneId);
    }
}
