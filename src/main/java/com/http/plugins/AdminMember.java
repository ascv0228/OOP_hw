package com.http.plugins;

import com.google.gson.Gson;
import com.http.structure.Permission;

public class AdminMember extends Member {
    // public class AdminMember{
    public AdminMember() {
        super(Permission.Administrative);
    }

    public AdminMember(MemberInfo mInfo) {
        super(mInfo, Permission.Administrative);
    }

    public static AdminMember CreateAdminMember(String gsonString) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, AdminMember.class);
    }

    @Override
    public boolean exec_record(OperationRecord record) {
        switch (record.get_operation()) {
            case CheckIn:
                return do_checkInBook(record.get_bookToken());
            case CheckOut:
                return do_checkOutBook(record.get_bookToken());
            case ReadOnline:
                return true;
            case Add:
            case Delete:
                return true;
            default:
                break;
        }
        return false;
    }
}
