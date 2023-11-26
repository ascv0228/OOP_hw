package com.http.plugins;

import com.google.gson.Gson;
import com.http.structure.BookForm;
import com.http.structure.BookStatus;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PBook extends Book {
    // private static final String LOCATION_FORMAT = "%dF";
    private String expireTime;
    private String location;

    public PBook(BookInfo bookInfo) {
        super(bookInfo, BookForm.pbook);
    }

    public PBook(BookInfo bookInfo, String location) {
        super(bookInfo, BookForm.pbook);
        this.location = location;
    }

    public PBook(String bookToken, BookInfo bookInfo) {
        super(bookToken, bookInfo, BookForm.pbook);
    }

    public PBook(String bookToken, BookInfo bookInfo, BookStatus bookStatus) {
        super(bookToken, bookInfo, BookForm.pbook, bookStatus);
    }

    public PBook(String bookToken, BookInfo bookInfo, BookStatus bookStatus, String location) {
        this(bookToken, bookInfo, bookStatus);
        this.location = location;
    }

    public static PBook CreatePBook(String gsonString) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, PBook.class);
    }

    public void set_expireTime(long timeValue) {
        if (timeValue == 0L) {
            this.expireTime = "";
            return;
        }

        Instant expirationInstant = Instant.ofEpochSecond(timeValue).plusSeconds(15 * 24 * 60 * 60);
        this.expireTime = ZonedDateTime.ofInstant(expirationInstant, ZoneId.of("GMT+8"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));

    }

    public String get_expireTime() {
        return this.expireTime;
    }

    public void set_location(String location) {
        this.location = location;
    }

    public String get_location() {
        return this.location;
    }

    @Override
    public boolean exec_record(OperationRecord record) {
        switch (record.get_operation()) {
            case CheckIn:
                return do_CheckInBook();
            case CheckOut:
                return do_CheckOutBook(record);
            case Delete:
                return do_DeleteBook();
            case Add:
                return do_AddBook();
            default:
                break;
        }
        return false;
    }

    private boolean do_CheckInBook() {
        if (!canCheckIn())
            return false;

        this.set_bookStatus(BookStatus.AVAILABLE);
        this.set_expireTime(0L);
        return true;
    }

    private boolean do_CheckOutBook(OperationRecord record) {
        if (!canCheckOut())
            return false;

        this.set_bookStatus(BookStatus.ON_LOAN);
        this.set_expireTime(record.get_timeValue());
        return true;
    }

    private boolean do_DeleteBook() {
        if (!canDelete())
            return false;

        this.set_bookStatus(BookStatus.DISCARDED);
        this.set_expireTime(0L);
        return true;
    }

    private boolean do_AddBook() {
        if (!canAdd())
            return false;

        this.set_bookStatus(BookStatus.AVAILABLE);
        this.set_expireTime(0L);
        return true;
    }

}
