package com.http.structure;

public enum Operation {
    CheckIn,
    CheckOut,
    Add,
    Delete,
    ReadOnline,
    UnModify;

    public static Operation getValueOrDefault(String name, Operation defaultValue) {
        try {
            return Operation.valueOf(name);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }
}
