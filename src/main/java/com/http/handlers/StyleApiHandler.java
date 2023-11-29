package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class StyleApiHandler extends BaseHandler {

    public StyleApiHandler() {
        this.path = "/style";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();
        sendStyleResponse(exchange, requestPath);
    }

}