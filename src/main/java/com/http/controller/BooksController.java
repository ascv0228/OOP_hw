package com.http.controller;

import java.util.Map;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.google.gson.Gson;
import com.http.plugins.Book;
import com.http.plugins.BookInfo;
import com.http.plugins.EBook;
import com.http.plugins.Member;
import com.http.plugins.OperationRecord;
import com.http.plugins.PBook;
import com.http.structure.BookForm;

import java.util.HashMap;
import java.util.List;

import org.bson.Document;

public class BooksController {
    private static MongoClient mongoClient;
    // private static MongoCollection<Document> collection;
    private Map<String, Book> books;

    public BooksController(MongoClient mongoClient) {
        BooksController.mongoClient = mongoClient;
        this.books = new HashMap<>();
        readDataBaseBook();

    }

    private static MongoCollection<Document> get_collection() {
        return mongoClient.getDatabase("OOP").getCollection("Books");
    }

    private void readDataBaseBook() {
        MongoCollection<Document> collection = get_collection();
        FindIterable<Document> documents = collection.find();

        Gson gson = new Gson();
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                String bookToken = document.getString("bookToken");
                BookForm pm = BookForm.values()[document.getInteger("bookForm")];
                String gsonString = document.getString("gson");
                if (pm == BookForm.ebook) {
                    EBook b = gson.fromJson(gsonString, EBook.class);
                    this.books.put(bookToken, b);

                } else if (pm == BookForm.pbook) {
                    // [WARN]: maybe lost "checkInBooks"
                    PBook b = gson.fromJson(gsonString, PBook.class);
                    this.books.put(bookToken, b);
                }

                System.out.println("bookToken: " + bookToken);
            }
        }

        System.out.println("=============readDataBaseBook=============");
    }

    public boolean addBook(Book b) {
        if (this.books.containsKey(b.get_bookToken())) {
            return false;
        }
        if (addDataBaseBook(b)) {
            this.books.put(b.get_bookToken(), b);
            return true;
        }
        return false;
    }

    private boolean addDataBaseBook(Book b) {
        MongoCollection<Document> collection = get_collection();
        try {
            Gson gson = new Gson();
            String jsonString = gson.toJson(b);
            Document document = new Document("bookToken", b.get_bookToken())
                    .append("bookForm", b.get_bookForm().ordinal())
                    .append("gson", jsonString);

            collection.insertOne(document);
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
        System.out.println("addDataBaseBook");
        return true;
    }

    public boolean updateBook(Book b) {
        if (!this.books.containsKey(b.get_bookToken())) {
            addDataBaseBook(b);
            this.books.put(b.get_bookToken(), b);
            return true;
        }
        if (updateDataBaseBook(b)) {
            this.books.put(b.get_bookToken(), b);
            return true;
        }
        return false;
    }

    private boolean updateDataBaseBook(Book b) {
        MongoCollection<Document> collection = get_collection();
        try {
            Document filter = new Document("bookToken", b.get_bookToken());
            Gson gson = new Gson();
            String jsonString = gson.toJson(b);
            Document document = new Document("bookToken", b.get_bookToken())
                    .append("bookForm", b.get_bookForm().ordinal())
                    .append("gson", jsonString);

            collection.replaceOne(filter, document);
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
        System.out.println("updateDataBaseBook");
        return true;
    }

    public boolean PrintDataBaseBook() {
        try {
            Gson gson = new Gson();
            for (Map.Entry<String, Book> entry : this.books.entrySet()) {
                String key = entry.getKey();
                Book value = entry.getValue();
                System.out.println("Key: " + key + ", Value: " + gson.toJson(value));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
        return true;
    }

    protected boolean ExecuteOperation(Member member, Book book, OperationRecord record) {
        boolean flag = true;
        try {
            flag &= book.exec_record(record);
        } catch (Exception e) {
            return false;
        }
        return flag;
    }

    public Book recoverBook(BookForm bookForm, String CopyBookString) {
        Gson gson = new Gson();
        switch (bookForm) {
            case ebook:
                return gson.fromJson(CopyBookString, EBook.class);

            case pbook:
                return gson.fromJson(CopyBookString, PBook.class);
        }
        return null;
    }

    public Book FindBook(String bookToken) {
        return this.books.getOrDefault(bookToken, null);
    }

    public Book createBook(String title, String description, String bookForm, String language,
            List<String> genres, String location) {
        BookInfo info = new BookInfo(title, description, language, genres);
        Book output;

        boolean isPBook = BookForm.getValueOrDefault(bookForm, BookForm.pbook) == BookForm.pbook;

        output = (isPBook) ? new PBook(info, location) : new EBook(info);

        addBook(output);
        return output;
    }

    public Map<String, Book> get_BooksMap() {
        return this.books;
    }

}
// if (retrieved instanceof Child) {
// Child retrievedChild = (Child) retrieved;
// System.out.println("Parent Field: " + retrievedChild.parentField);
// System.out.println("Child Field: " + retrievedChild.childField);
// } else {
// System.out.println("Not an instance of Child");
// }