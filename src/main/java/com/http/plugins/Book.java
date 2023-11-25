package com.http.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.http.structure.BookForm;
import com.http.structure.BookStatus;

public abstract class Book {
    private String bookToken;
    private BookForm bookForm;
    private BookInfo bookInfo;
    private BookStatus bookStatus;
    private List<OperationRecord> records;

    public Book(String bookToken, BookInfo bookInfo, BookForm bookForm, BookStatus bookStatus) {
        this.bookToken = bookToken;
        this.bookInfo = bookInfo;
        this.bookForm = bookForm;
        this.bookStatus = bookStatus;
    }

    public Book(String bookToken, BookInfo bookInfo, BookForm bookForm) {
        this(bookToken, bookInfo, bookForm, BookStatus.UNKNOWN);
    }

    public Book(BookInfo bookInfo, BookForm bookForm) {
        this(UUID.randomUUID().toString(), bookInfo, bookForm, BookStatus.UNKNOWN);
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public abstract boolean exec_record(OperationRecord record);

    public String get_bookToken() {
        return this.bookToken;
    }

    public BookForm get_bookForm() {
        return this.bookForm;
    }

    public BookInfo get_bookInfo() {
        return this.bookInfo;
    }

    public void set_bookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public BookStatus get_bookStatus() {
        return this.bookStatus;
    }

    public List<OperationRecord> get_records() {
        return this.records;
    }

    public void AddOperationRecord(OperationRecord op) {
        if (this.records.size() == 0) {
            this.records = new ArrayList<>();
        }
        this.records.add(op);
    }

    public boolean isEBook() {
        return this.get_bookForm() == BookForm.ebook;
    }

    public boolean isPBook() {
        return this.get_bookForm() == BookForm.pbook;
    }

    public boolean canCheckIn() {
        return this.get_bookStatus() == BookStatus.ON_LOAN;
    }

    public boolean canCheckOut() {
        return this.get_bookStatus() == BookStatus.AVAILABLE;
    }

    public boolean canAdd() {
        return this.get_bookStatus() == BookStatus.UNKNOWN || this.get_bookStatus() == BookStatus.DISCARDED;
    }

    public boolean canDelete() {
        return this.get_bookStatus() != BookStatus.UNKNOWN && this.get_bookStatus() != BookStatus.ON_LOAN;
    }

}
