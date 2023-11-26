package com.http.plugins;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.http.structure.Operation;

public class OperationRecord {
    private String userToken;
    private String bookToken;
    private Operation operation;
    private long timeValue;
    private String timeString;
    // private ZonedDateTime time;

    public OperationRecord(String userToken, String bookToken, Operation operation, ZonedDateTime zonedDateTime) {
        this.userToken = userToken;
        this.bookToken = bookToken;
        this.operation = operation;
        this.timeValue = zonedDateTime.toInstant().getEpochSecond();
        this.timeString = zonedDateTime.withZoneSameInstant(ZoneId.of("GMT+8"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
    }

    public OperationRecord(String userToken, String bookToken, Operation operation) {
        this(userToken, bookToken, operation,
                ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.ofHours(8)));
    }

    public String get_userToken() {
        return this.userToken;
    }

    public String get_bookToken() {
        return this.bookToken;
    }

    public Operation get_operation() {
        return this.operation;
    }

    public long get_timeValue() {
        return this.timeValue;
    }

    public String get_timeString() {
        return this.timeString;
    }

}