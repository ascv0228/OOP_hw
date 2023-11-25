package com.http.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.http.structure.Permission;

public abstract class Member {
    private String userToken;
    private MemberInfo memberInfo;
    private Permission permission;
    private List<OperationRecord> records;
    protected List<String> CheckOutBooks; // bookTokens

    protected Member(String userToken, MemberInfo memberInfo, Permission permission) {
        this.userToken = userToken;
        this.memberInfo = memberInfo;
        this.permission = permission;
        this.records = new ArrayList<>();
    }

    protected Member(MemberInfo memberInfo, Permission permission) {
        this(UUID.randomUUID().toString(), memberInfo, permission);
    }

    protected Member(Permission permission) {
        this(new MemberInfo("man"), permission);
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String get_userToken() {
        return this.userToken;
    }

    public MemberInfo get_memberInfo() {
        return this.memberInfo;
    }

    public Permission get_permission() {
        return this.permission;
    }

    public List<OperationRecord> get_records() {
        return this.records;
    }

    public void AddOperationRecord(OperationRecord op) {
        if (this.records == null) {
            this.records = new ArrayList<>();
        }
        this.records.add(op);
    }

    public boolean isAdmin() {
        return this.permission == Permission.Administrative;
    }

    public boolean isRegular() {
        return this.permission == Permission.Regular;
    }

    public abstract boolean exec_record(OperationRecord record);

    public int get_CheckOutBooksNumber() {
        return this.CheckOutBooks.size();
    }

    public List<String> get_CheckOutBooks() {
        return this.CheckOutBooks;
    }

    protected boolean do_checkOutBook(String bookToken) {
        if (this.CheckOutBooks == null)
            this.CheckOutBooks = new ArrayList<>();
        this.CheckOutBooks.add(bookToken);
        return true;
    }

    protected boolean do_checkInBook(String bookToken) {
        if (this.CheckOutBooks == null) {
            this.CheckOutBooks = new ArrayList<>();
            return false;
        }
        if (this.hasBooks(bookToken))
            this.CheckOutBooks.remove(bookToken);
        return true;
    }

    public boolean hasBooks(String bookToken) {
        return this.CheckOutBooks.contains(bookToken);
    }
}
