package com.http;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;

import java.util.logging.Handler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MongodbConnect {
    private static MongoClient mongoClient;

    public static MongoClient connectToMongoDB() {
        // MongoDB connection code

        String uriString = "mongodb+srv://b083022053:TEST@ascv0228.t0qjgal.mongodb.net/?retryWrites=true&w=majority";
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        for (Handler handler : mongoLogger.getHandlers()) {
            handler.setLevel(Level.WARNING);
        }
        System.out.println("run: " + uriString);
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uriString))
                .serverApi(serverApi)
                .build();

        mongoClient = MongoClients.create(settings);

        return mongoClient;
    }

}
