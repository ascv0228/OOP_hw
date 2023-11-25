package com.http.plugins;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.http.structure.Operation;

public class OperationRecord {
    private String userToken;
    private String bookToken;
    private Operation operation;
    private ZonedDateTime time;

    public OperationRecord(String userToken, String bookToken, Operation operation, ZonedDateTime time){
        this.userToken = userToken;
        this.bookToken = bookToken;
        this.operation = operation;
        this.time = time;
    }

    public OperationRecord(String userToken, String bookToken, Operation operation){
        this( userToken, bookToken, operation,
            ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.ofHours(8))
        );
    }


    public String time_format(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        return this.time.format(formatter);
    }

    public String get_userToken(){
        return this.userToken;
    }
    public String get_bookToken(){
        return this.bookToken;
    }
    public Operation get_operation(){
        return this.operation;
    }

}