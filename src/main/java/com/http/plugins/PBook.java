package com.http.plugins;

import java.util.List;

import com.google.gson.Gson;
import com.http.structure.BookForm;
import com.http.structure.BookStatus;

import java.util.ArrayList;
import java.time.ZonedDateTime;

public class PBook extends Book {
    // private static final String LOCATION_FORMAT = "%dF";
    private ZonedDateTime expireTime;
    private String location;

    public PBook(String bookToken, BookInfo bookInfo) {
        super(bookToken, bookInfo, BookForm.pbook);
    }

    public PBook(String bookToken, BookInfo bookInfo, BookStatus bookStatus) {
        super(bookToken, bookInfo, BookForm.pbook, bookStatus);
    }

    public static PBook CreatePBook(String gsonString) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, PBook.class);
    }

    public void set_expireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public ZonedDateTime get_expireTime() {
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
                return do_CheckOutBook();
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
        return true;
    }

    private boolean do_CheckOutBook() {
        if (!canCheckOut())
            return false;

        this.set_bookStatus(BookStatus.ON_LOAN);
        return true;
    }

    private boolean do_DeleteBook() {
        if (!canDelete())
            return false;

        this.set_bookStatus(BookStatus.DISCARDED);
        return true;
    }

    private boolean do_AddBook() {
        if (!canAdd())
            return false;

        this.set_bookStatus(BookStatus.AVAILABLE);
        return true;
    }

}
