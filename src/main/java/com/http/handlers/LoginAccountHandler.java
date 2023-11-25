package com.http.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import com.http.SimpleHttpServer;
import com.http.controller.BaseController;
import com.http.plugins.RegularMember;
import com.sun.net.httpserver.HttpExchange;

public final class LoginAccountHandler extends BaseHandler {

    public LoginAccountHandler() {
        this.path = "/loginAccount";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 处理HTTP请求

        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        String name = params.getOrDefault("userToken", "None");
        System.out.println(name);
        String member = SimpleHttpServer.getBaseController().createRegularMember(name);
        String response = "Login member:\n" + member;

        sendResponse(exchange, response);
    }

}
