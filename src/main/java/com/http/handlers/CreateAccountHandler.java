package com.http.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import com.http.SimpleHttpServer;
import com.http.controller.BaseController;
import com.http.plugins.RegularMember;
import com.sun.net.httpserver.HttpExchange;

public final class CreateAccountHandler extends BaseHandler {

    public CreateAccountHandler() {
        this.path = "/createRegularAccount";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 处理HTTP请求

        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        String name = params.getOrDefault("name", "Guest");
        String member = SimpleHttpServer.getBaseController().createRegularMember(name);
        String response = "create a Regular member:\n" + member;

        sendResponse(exchange, response);
    }

}
