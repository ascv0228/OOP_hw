
package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class BookCollectHandler extends BaseHandler {

    public BookCollectHandler() {
        this.path = "/bookCollect";
        this.htmlPath = "bookCollect.html";
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
        String response = SimpleHttpServer.getBaseController().get_booksMap();

        sendResponse(exchange, response);
    }

}
