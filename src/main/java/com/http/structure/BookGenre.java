package com.http.structure;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public enum BookGenre {
    HORROR("Horror"),
    ROMANCE("Romance"),
    MYSTERY("Mystery"),
    SCIFI("Science Fiction"),
    FANTASY("Fantasy"),
    THRILLER("Thriller"),
    HISTORICAL_FICTION("Historical Fiction"),
    NON_FICTION("Non-Fiction");

    private String displayName;

    private BookGenre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static BookGenre getValueOrDefault(String value, BookGenre defaultValue) {
        try {
            return BookGenre.valueOf(value);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public static List<BookGenre> ListStringToBookGenre(List<String> values) {
        List<BookGenre> output = new ArrayList<>();
        for (String value : values) {
            try {
                output.add(BookGenre.valueOf(value.toUpperCase()));
            } catch (IllegalArgumentException e) {
            }
        }
        return output;
    }

}
