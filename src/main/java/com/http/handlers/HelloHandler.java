package com.http.handlers;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

public final class HelloHandler extends BaseHandler {

    public HelloHandler() {
        this.path = "/hello";
        this.parameters = List.of("name");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        if (!checkParameter(params)) {
            String response = sendErrorResponse();
            sendResponse(exchange, response);
        }
        String response = "Hello, " + params.get("name") + "!";

        sendResponse(exchange, response);
    }

}
