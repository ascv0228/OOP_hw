package com.http.plugins;

import java.util.List;

import com.google.gson.Gson;
import com.http.structure.BookForm;
import com.http.structure.BookStatus;

import java.util.ArrayList;

public class EBook extends Book {
    private List<String> allowedTokens;

    public EBook(BookInfo bookInfo) {
        super(bookInfo, BookForm.ebook);
        this.allowedTokens = new ArrayList<>();
    }

    public EBook(String bookToken, BookInfo bookInfo) {
        super(bookToken, bookInfo, BookForm.ebook);
        this.allowedTokens = new ArrayList<>();
    }

    public EBook(String bookToken, BookInfo bookInfo, List<String> allowedTokens) {
        super(bookToken, bookInfo, BookForm.ebook);
        this.allowedTokens = allowedTokens;
    }

    public static EBook CreatePBook(String gsonString) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, EBook.class);
    }

    public void Add_AllowedTokens(String memberToken) {
        this.allowedTokens.add(memberToken);
    }

    public boolean is_MemberAllowed(Member member) {
        if (member.isAdmin()) {
            return true;
        }
        if (allowedTokens.contains(member.get_userToken())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean exec_record(OperationRecord record) {
        switch (record.get_operation()) {
            case ReadOnline:
                return true;
            case CheckIn:
            case CheckOut:
                return true;
            case Delete:
                return do_DeleteBook();

            case Add:
                return do_AddBook();
            default:
                break;
        }
        return false;
    }

    private boolean do_DeleteBook() {
        if (this.get_bookStatus() == BookStatus.DISCARDED)
            return false;

        this.set_bookStatus(BookStatus.DISCARDED);
        return true;
    }

    private boolean do_AddBook() {
        if (this.get_bookStatus() != BookStatus.UNKNOWN || this.get_bookStatus() != BookStatus.DISCARDED)
            return false;

        this.set_bookStatus(BookStatus.AVAILABLE);
        return true;
    }
}
