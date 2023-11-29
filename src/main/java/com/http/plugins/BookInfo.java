package com.http.plugins;

import java.util.List;

import com.google.gson.Gson;
import com.http.structure.BookGenre;
import com.http.structure.Language;

public class BookInfo {
    private String title;
    private String description;
    private Language language;
    private List<BookGenre> genres;

    public BookInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public BookInfo(String title, String description, Language language, List<BookGenre> genres) {
        this(title, description);
        this.language = language;
        this.genres = genres;
    }

    public BookInfo(String title, String description, String language, List<String> genres) {
        this(title, description, Language.getValueOrDefault(language, Language.CHINESE),
                BookGenre.ListStringToBookGenre(genres));
    }

    public String get_title() {
        return this.title;
    }

    public String get_description() {
        return this.description;
    }

    public Language get_language() {
        return this.language;
    }

    public List<BookGenre> get_genres() {
        return this.genres;
    }
}
