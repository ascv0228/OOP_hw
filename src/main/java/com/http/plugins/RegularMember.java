package com.http.plugins;

import com.google.gson.Gson;
import com.http.structure.Permission;

public class RegularMember extends Member {

    public RegularMember() {
        super(Permission.Regular);
    }

    public RegularMember(MemberInfo mInfo) {
        super(mInfo, Permission.Regular);
    }

    public RegularMember(String userToken, MemberInfo mInfo) {
        super(userToken, mInfo, Permission.Regular);
    }

    public static RegularMember CreateRegularMember(String gsonString) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, RegularMember.class);
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
            default:
                break;
        }
        return false;
    }

}
