
package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class BookCollectHandler extends BaseHandler {

    public BookCollectHandler() {
        this.path = "/bookCollect";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = SimpleHttpServer.getBaseController().get_booksMap();

        sendResponse(exchange, response);
    }

}
