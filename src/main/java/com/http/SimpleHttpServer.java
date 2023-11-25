package com.http;

import com.sun.net.httpserver.HttpServer;
import org.reflections.Reflections;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.Iterator;

import com.http.controller.BaseController;
import com.http.handlers.*;
import com.mongodb.client.MongoClient;

public class SimpleHttpServer {
    private static final String IP = "http://localhost";
    private static final int PORT = 8080;
    private static BaseController baseController;

    public static void main(String[] args) throws IOException {
        MongoClient mongoClient = MongodbConnect.connectToMongoDB();
        SimpleHttpServer.baseController = new BaseController(mongoClient);
        System.out.println("run: " + SimpleHttpServer.class.getName());

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        Reflections reflections = new Reflections("com.http.handlers");
        // Get all classes in the package
        try {
            Set<Class<? extends BaseHandler>> allClasses = reflections.getSubTypesOf(BaseHandler.class);

            Iterator<Class<? extends BaseHandler>> iterator = allClasses.iterator();
            while (iterator.hasNext()) {
                Class<? extends BaseHandler> element = iterator.next();
                createContext(server, element.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start Server
        server.setExecutor(null); // default executor
        server.start();
        // mongoClient.close();
    }

    private static void createContext(HttpServer server, BaseHandler handler) {
        System.out.println("path: " + IP + ":" + PORT + handler.get_path());
        server.createContext(handler.get_path(), handler);
    }

    public static BaseController getBaseController() {
        return SimpleHttpServer.baseController;
    }

}
