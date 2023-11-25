package com.http.plugins;

public class BookInfo {
    private String title;
    private String description;

    public BookInfo(String title, String description){
        this.title = title;
        this.description = description;
    }
    public String get_title(){
        return this.title;
    }
    public String get_description(){
        return this.description;
    }
}
