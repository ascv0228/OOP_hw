package com.http.structure;

public enum Language {
    ENGLISH("en"),
    CHINESE("zh"),
    JAPANESE("ja"),
    RUSSIAN("ru");

    private String value;

    private Language(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Language getValueOrDefault(String value, Language defaultValue) {
        try {
            return Language.valueOf(value);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }
}