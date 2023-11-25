package com.http;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;

import java.util.Base64;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongodbConnect {
    private static MongoClient mongoClient;

    public static MongoClient connectToMongoDB() {
        // MongoDB connection code

        String uriString = "bW9uZ29kYitzcnY6Ly9URVNUOlRFU1RAYXNjdjAyMjgudDBxamdhbC5tb25nb2RiLm5ldC8/cmV0cnlXcml0ZXM9dHJ1ZSZ3PW1ham9yaXR5";
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        for (Handler handler : mongoLogger.getHandlers()) {
            handler.setLevel(Level.WARNING);
        }
        System.out.println("run: connectToMongoDB");
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(new String(Base64.getDecoder().decode(uriString))))
                .serverApi(serverApi)
                .build();

        mongoClient = MongoClients.create(settings);

        return mongoClient;
    }

}
