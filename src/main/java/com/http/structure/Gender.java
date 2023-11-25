package com.http.structure;

public enum Gender {
    Male,
    Female;

    public static Gender getValueOrDefault(String value, Gender defaultValue) {
        try {
            return Gender.valueOf(value);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }
}
