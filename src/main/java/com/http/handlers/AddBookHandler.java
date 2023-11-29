package com.http.handlers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class AddBookHandler extends BaseHandler {

    public AddBookHandler() {
        this.path = "/addBook";
        this.parameters = List.of("title", "description", "bookForm", "language", "bookGenres",
                "location");
        this.htmlPath = "addBook.html";
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
        String userToken = parseCookies(exchange).get("userToken");
        boolean isAuthorized = SimpleHttpServer.getBaseController().get_isAdmin(userToken);
        String response;
        if (isAuthorized) {
            List<String> bookGenres = Arrays.asList(params.get("bookGenres").split("\\+"));
            String book = SimpleHttpServer.getBaseController().addBook(userToken,
                    params.get("title"), params.get("description"), params.get("bookForm"), params.get("language"),
                    bookGenres,
                    params.get("location"));
            response = book;
        } else {
            response = null;
        }

        sendResponse(exchange, response);
    }

}