package com.http.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.http.plugins.Book;
import com.http.plugins.EBook;
import com.http.plugins.Member;
import com.http.plugins.MemberInfo;
import com.http.plugins.OperationRecord;
import com.http.plugins.PBook;
import com.http.plugins.RegularMember;
import com.http.structure.BookForm;
import com.http.structure.Operation;
import com.http.structure.Permission;
import com.mongodb.client.MongoClient;

public class BaseController {
    private static MongoClient mongoClient;
    private static MembersController mController;
    private static BooksController bController;
    public final int bookLimit = 5;

    public BaseController(MongoClient mongoClient) {
        BaseController.mongoClient = mongoClient;
        mController = new MembersController(mongoClient);
        bController = new BooksController(mongoClient);
    }

    public boolean TODO_ExecuteOperation(String userToken, String bookToken, Operation op) {
        Member member = mController.FindMember(userToken);
        Book book = bController.FindBook(bookToken);
        return Is_SuccessExecuteOperation(member, book, op);
    }

    public boolean TODO_ExecuteOperation(String userToken, String bookToken, String opString) {
        Operation op = Operation.getValueOrDefault(opString, Operation.UnModify);
        return TODO_ExecuteOperation(userToken, bookToken, op);
    }

    public boolean Is_SuccessExecuteOperation(Member member, Book book, Operation op) {
        boolean flag = true;
        if (member == null || book == null || op == null)
            return false;
        BookForm bookForm = book.get_bookForm();
        String CopyBookString = book.toString();

        Permission permission = member.get_permission();
        String CopyMemberString = member.toString();
        OperationRecord record = new OperationRecord(member.get_userToken(),
                book.get_bookToken(), op);
        switch (op) {
            case CheckIn:
                if (!hasEligible_CheckInBook(member, book))
                    return false;
                break;
            case CheckOut:
                if (!hasEligible_CheckOutBook(member, book))
                    return false;
                break;
            case Add:
                if (!hasEligible_AddBook(member, book))
                    return false;
                break;
            case Delete:
                if (!hasEligible_DeleteBook(member, book))
                    return false;
                break;
            case ReadOnline:
                if (!hasEligible_ReadOnlineBook(member, book))
                    return false;
                break;
            case UnModify:
                return true;

            default:
                return false;
        }
        flag &= ExecuteOperation(member, book, record);
        if (!flag) {
            handleFailure_callback(bookForm, CopyBookString, permission, CopyMemberString);
        } else {
            handleSuccess_callback(member, book, record);
        }
        System.out.println("flag: " + flag);
        return flag;
    }

    public boolean ExecuteOperation(Member member, Book book, OperationRecord record) {
        boolean flag = true;
        try {
            flag &= mController.ExecuteOperation(member, book, record);
            flag &= bController.ExecuteOperation(member, book, record);

        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    private void handleFailure_callback(BookForm bookForm, String CopyBookString,
            Permission permission, String CopyMemberString) {
        mController.recoverMember(permission, CopyMemberString);
        bController.recoverBook(bookForm, CopyBookString);
    }

    private void handleSuccess_callback(Member member, Book book, OperationRecord record) {
        member.AddOperationRecord(record);
        book.AddOperationRecord(record);
        mController.updateMember(member);
        bController.updateBook(book);
    }

    private boolean hasEligible_CheckInBook(Member member, Book book) {
        boolean flag = true;
        if (book.isEBook()) {
            return false;
        }
        if (book.isPBook()) {
            flag &= ((PBook) book).canCheckIn();
        }
        if (member.isRegular()) {
            flag &= ((RegularMember) member).hasBooks(book.get_bookToken());
        }
        return flag;
    }

    private boolean hasEligible_CheckOutBook(Member member, Book book) {
        boolean flag = true;
        if (book.isEBook()) {
            return false;
        }
        if (book.isPBook()) {
            flag &= ((PBook) book).canCheckOut();
        }
        if (member.isRegular()) {
            flag &= ((RegularMember) member).get_CheckOutBooksNumber() < bookLimit;
        } else if (member.isAdmin()) {
            flag &= true;
        }
        return flag;
    }

    private boolean hasEligible_DeleteBook(Member member, Book book) {
        return member.isAdmin();
    }

    private boolean hasEligible_AddBook(Member member, Book book) {
        return member.isAdmin();
    }

    private boolean hasEligible_ReadOnlineBook(Member member, Book book) {
        if (book.isEBook()) {
            return ((EBook) book).is_MemberAllowed(member);
        }
        return false;
    }

    public String createMember(String name, String authority, String gender) {
        MemberInfo info = new MemberInfo(name, gender);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(
                (authority.equals("admin")) ? mController.createAdminMember(info)
                        : mController.createRegularMember(info));
    }

    public String addBook(String userToken, String title, String description, String bookForm) {
        if (!get_isAdmin(userToken))
            return "Failure";

        Book book = bController.createBook(title, description, bookForm);
        boolean result = TODO_ExecuteOperation(userToken, book.get_bookToken(), Operation.Add);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return result ? gson.toJson(book) : "Failure";

    }

    public String get_LoginAccount(String userToken) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Member m = mController.FindMember(userToken);
        return m == null ? "Failure" : gson.toJson(m);
    }

    public String get_BookInfo(String bookToken) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Book m = bController.FindBook(bookToken);
        return m == null ? "Failure" : gson.toJson(m);
    }

    public boolean get_isAdmin(String userToken) {
        return mController.FindMember(userToken).isAdmin();
    }

    public boolean get_isRegular(String userToken) {
        return mController.FindMember(userToken).isRegular();
    }

    public boolean get_isPBook(String bookToken) {
        return bController.FindBook(bookToken).isPBook();
    }

    public boolean get_isEBook(String bookToken) {
        return bController.FindBook(bookToken).isEBook();
    }
}
