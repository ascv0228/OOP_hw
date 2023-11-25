package com.http.structure;

public enum BookForm {
    pbook, ebook;

    public static BookForm getValueOrDefault(String value, BookForm defaultValue) {
        try {
            return BookForm.valueOf(value);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }
}
