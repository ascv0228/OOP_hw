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
        this.parameters = List.of("bookToken", "operation");
        this.responseFormat = "Member Info\n" +
                "%s\n" +
                "Book Info\n" +
                "%s\n";
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
        boolean result = SimpleHttpServer.getBaseController().TODO_ExecuteOperation(
                userToken, params.get("bookToken"), params.get("operation"));
        String response;
        if (result) {
            String member = SimpleHttpServer.getBaseController().get_MemberInfo(userToken);
            String book = SimpleHttpServer.getBaseController().get_BookInfo(params.get("bookToken"));
            response = String.format(responseFormat, member, book);
        } else {
            response = "Operation Error";
        }

        sendResponse(exchange, response);
    }
}