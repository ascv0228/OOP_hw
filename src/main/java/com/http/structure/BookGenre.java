package com.http.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum BookGenre {
    HORROR("Horror"),
    ROMANCE("Romance"),
    MYSTERY("Mystery"),
    SCIFI("ScienceFiction"),
    FANTASY("Fantasy"),
    THRILLER("Thriller"),
    HISTORICAL_FICTION("HistoricalFiction"),
    NON_FICTION("Non-Fiction");

    private String displayName;

    private BookGenre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Map<String, BookGenre> stringMap = Arrays.stream(values())
            .collect(Collectors.toMap(BookGenre::getDisplayName, Function.identity()));

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
                output.add(stringMap.get(value));
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }
        return output;
    }

}
