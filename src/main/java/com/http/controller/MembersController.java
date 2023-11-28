package com.http.controller;

import java.util.Map;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.google.gson.Gson;
import com.http.plugins.AdminMember;
import com.http.plugins.Book;
import com.http.plugins.Member;
import com.http.plugins.MemberInfo;
import com.http.plugins.OperationRecord;
import com.http.plugins.RegularMember;
import com.http.structure.Permission;

import java.util.HashMap;

import org.bson.Document;

public class MembersController {
    private static MongoClient mongoClient;
    private Map<String, Member> members;

    public MembersController(MongoClient mongoClient) {
        MembersController.mongoClient = mongoClient;
        this.members = new HashMap<>();
        readDataBaseMember();

    }

    private static MongoCollection<Document> get_collection() {
        return mongoClient.getDatabase("OOP").getCollection("Members");
    }

    private void readDataBaseMember() {
        MongoCollection<Document> collection = get_collection();
        FindIterable<Document> documents = collection.find();
        Gson gson = new Gson();
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                String userToken = document.getString("userToken");
                Permission pm = Permission.values()[document.getInteger("permission")];
                String gsonString = document.getString("gson");
                if (pm == Permission.Administrative) {
                    Member m = gson.fromJson(gsonString, AdminMember.class);
                    this.members.put(userToken, m);

                } else if (pm == Permission.Regular) {
                    Member m = gson.fromJson(gsonString, RegularMember.class);
                    this.members.put(userToken, m);
                }
                System.out.println("userToken: " + userToken);
            }
        }
        System.out.println("=============readDataBaseMember=============");
    }

    public RegularMember createRegularMember(MemberInfo info) {
        RegularMember output = new RegularMember(info);
        addMember(output);
        return output;
    }

    public AdminMember createAdminMember(MemberInfo info) {
        AdminMember output = new AdminMember(info);
        addMember(output);
        return output;
    }

    public boolean addMember(Member m) {
        if (this.members.containsKey(m.get_userToken())) {
            return false;
        }
        if (addDataBaseMember(m)) {
            this.members.put(m.get_userToken(), m);
            return true;
        }
        return false;
    }

    private boolean addDataBaseMember(Member m) {
        MongoCollection<Document> collection = get_collection();
        try {
            Gson gson = new Gson();
            String jsonString = gson.toJson(m);
            Document document = new Document("userToken", m.get_userToken())
                    .append("permission", m.get_permission().ordinal())
                    .append("gson", jsonString);

            collection.insertOne(document);
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }

        System.out.println("addDataBaseMember");
        System.out.println("userToken: " + m.get_userToken());
        return true;
    }

    public boolean updateMember(Member m) {
        if (!this.members.containsKey(m.get_userToken())) {
            addDataBaseMember(m);
            this.members.put(m.get_userToken(), m);
            return true;
        }
        if (updateDataBaseMember(m)) {
            this.members.put(m.get_userToken(), m);
            return true;
        }
        return false;
    }

    private boolean updateDataBaseMember(Member m) {
        MongoCollection<Document> collection = get_collection();
        try {
            Document filter = new Document("userToken", m.get_userToken());
            Gson gson = new Gson();
            String jsonString = gson.toJson(m);
            Document document = new Document("userToken", m.get_userToken())
                    .append("permission", m.get_permission().ordinal())
                    .append("gson", jsonString);

            collection.replaceOne(filter, document);
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }

        System.out.println("updateDataBaseMember");
        return true;
    }

    public Member FindMember(String userToken) {
        return this.members.getOrDefault(userToken, null);
    }

    public boolean PrintDataBaseMember() {
        try {
            Gson gson = new Gson();
            for (Map.Entry<String, Member> entry : this.members.entrySet()) {
                String key = entry.getKey();
                Member value = entry.getValue();
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
            flag &= member.exec_record(record);
        } catch (Exception e) {
            return false;
        }
        return flag;

    }

    public Member recoverMember(Permission permission, String CopyMemberString) {
        Gson gson = new Gson();
        Member output = null;
        switch (permission) {
            case Administrative:
                output = gson.fromJson(CopyMemberString, AdminMember.class);

            case Regular:
                output = gson.fromJson(CopyMemberString, RegularMember.class);
        }
        if (output != null) {
            this.updateMember(output);
        }
        return output;
    }
}
