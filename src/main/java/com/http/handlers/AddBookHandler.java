package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class AddBookHandler extends BaseHandler {

    public AddBookHandler() {
        this.path = "/addBook";

        this.htmlPath = "addBook.html";
        this.parameters = List.of("userToken", "title", "description", "bookForm");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 处理HTTP请求
        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        if (params.size() == 0) {
            sendHtml(exchange);
        }
        if (!checkParameter(params)) {
            String response = sendErrorResponse();
            sendResponse(exchange, response);
        }

        boolean isAuthorized = SimpleHttpServer.getBaseController().get_isAdmin(params.get("userToken"));
        String response;
        if (isAuthorized) {
            String book = SimpleHttpServer.getBaseController().addBook(params.get("userToken"),
                    params.get("title"), params.get("description"), params.get("bookForm"));
            response = String.format("Operation Handler\n" + "Book Info\n" + "%s", book);
        } else {
            response = "Operation Error";
        }

        sendResponse(exchange, response);
    }
}