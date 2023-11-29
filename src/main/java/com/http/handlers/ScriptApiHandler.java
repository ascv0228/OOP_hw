package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class ScriptApiHandler extends BaseHandler {

    public ScriptApiHandler() {
        this.path = "/script";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();

        sendScriptResponse(exchange, requestPath);
    }

}