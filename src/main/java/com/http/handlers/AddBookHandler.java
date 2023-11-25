package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class AddBookHandler extends BaseHandler {

    public AddBookHandler() {
        this.path = "/addBook";
        this.parameters = List.of("userToken", "title", "description", "bookForm");
        this.responseFormat = "Operation Handler\n" + "Book Info\n" + "%s";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 处理HTTP请求

        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        if (!checkParameter(params)) {
            String response = sendErrorResponse();
            sendResponse(exchange, response);
        }

        boolean isAuthorized = SimpleHttpServer.getBaseController().get_isAdmin(params.get("userToken"));
        String response;
        if (isAuthorized) {
            String book = SimpleHttpServer.getBaseController().addBook(params.get("userToken"),
                    params.get("title"), params.get("description"), params.get("bookForm"));
            response = String.format(responseFormat, book/* .replaceAll("\\n|\\r\\n", "<br>") */);
        } else {
            response = "Operation Error";
        }

        sendResponse(exchange, response);
    }
}