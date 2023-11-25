package com.http.plugins;

import com.http.structure.Gender;

public class MemberInfo {
    private String name;
    private Gender gender;

    public MemberInfo(String name) {
        this(name, Gender.Male);
    }

    public MemberInfo(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public MemberInfo(String name, String gender) {
        this(name, Gender.getValueOrDefault(gender, Gender.Male));
    }

    public String get_name() {
        return this.name;
    }

    public Gender get_gender() {
        return this.gender;
    }
}
