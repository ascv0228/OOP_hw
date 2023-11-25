package com.http;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.http.controller.BaseController;
import com.http.controller.BooksController;
import com.http.controller.MembersController;
import com.http.handlers.BaseHandler;
import com.http.handlers.CreateAccountHandler;
import com.http.handlers.HelloHandler;
import com.http.handlers.LoginAccountHandler;
import com.http.plugins.AdminMember;
import com.mongodb.client.MongoClient;

public class SimpleHttpServer {
    private static final String IP = "http://localhost";
    private static final int PORT = 8080;
    private static BaseController baseController;

    public static void main(String[] args) throws IOException {
        // 创建HttpServer实例并绑定到指定端口

        MongoClient mongoClient = MongodbConnect.connectToMongoDB();
        SimpleHttpServer.baseController = new BaseController(mongoClient);
        System.out.println("run: " + SimpleHttpServer.class.getName());

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        // mController.addDataBaseMember(new AdminMember());

        // 创建上下文路径，将路径映射到处理程序
        createContext(server, new HelloHandler());
        createContext(server, new CreateAccountHandler());
        createContext(server, new LoginAccountHandler());

        // 启动服务器
        server.setExecutor(null); // 使用默认的executor
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
