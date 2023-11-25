package com.http.handlers;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.Map;

public final class HelloHandler extends BaseHandler {

    public HelloHandler() {
        this.path = "/hello";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 处理HTTP请求
        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        String name = params.getOrDefault("name", "Guest");
        String response = "Hello, " + name + "!";

        sendResponse(exchange, response);
    }

}
