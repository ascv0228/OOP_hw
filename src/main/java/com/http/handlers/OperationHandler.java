package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class OperationHandler extends BaseHandler {

    public OperationHandler() {
        this.path = "/operation";
        this.htmlPath = "operation.html";
        this.parameters = List.of("userToken", "bookToken", "operation");
        this.responseFormat = "Operation Handler\n" +
                "Member Info\n" +
                "%s\n" +
                "Book Info" +
                "%s\n";
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

        boolean result = SimpleHttpServer.getBaseController().TODO_ExecuteOperation(
                params.get("userToken"), params.get("bookToken"), params.get("operation"));
        String response;
        if (result) {
            String member = SimpleHttpServer.getBaseController().get_LoginAccount(params.get("userToken"));
            String book = SimpleHttpServer.getBaseController().get_BookInfo(params.get("bookToken"));
            response = String.format(responseFormat, member, book);
        } else {
            response = "Operation Error";
        }

        sendResponse(exchange, response);
    }
}