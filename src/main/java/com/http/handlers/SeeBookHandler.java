package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class SeeBookHandler extends BaseHandler {

    public SeeBookHandler() {
        this.path = "/seeBook";
        this.parameters = List.of("bookToken");
        this.htmlPath = "seeBook.html";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (isApiPath(exchange)) {
            handleApi(exchange);
            return;
        }
        handlePage(exchange);
        return;
    }

    public void handlePage(HttpExchange exchange) throws IOException {
        sendHtml(exchange);
    }

    public void handleApi(HttpExchange exchange) throws IOException {
        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        if (!checkParameter(params)) {
            sendErrorResponse(exchange);
            return;
        }
        String book = SimpleHttpServer.getBaseController().get_BookInfo(params.get("bookToken"));
        String response = book;

        sendResponse(exchange, response);
    }

}