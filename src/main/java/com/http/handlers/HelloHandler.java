package com.http.handlers;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class HelloHandler extends BaseHandler {

    public HelloHandler() {
        this.path = "/hello";
        this.htmlPath = "hello.html";
        this.parameters = List.of("name");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRequestURI().getQuery());
        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        if (params.size() == 0) {
            sendHtml(exchange);
        }
        if (!checkParameter(params)) {
            String response = sendErrorResponse();
            sendResponse(exchange, response);
        }
        String response = "Hello, " + params.get("name") + "!";

        sendResponse(exchange, response);
    }

}
