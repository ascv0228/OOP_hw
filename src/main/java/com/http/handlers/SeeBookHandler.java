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
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 处理HTTP请求

        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        if (!checkParameter(params)) {
            String response = sendErrorResponse();
            sendResponse(exchange, response);
        }
        String book = SimpleHttpServer.getBaseController().get_BookInfo(params.get("bookToken"));
        String response = "BookInfo:\n" + book;

        sendResponse(exchange, response);
    }

}
