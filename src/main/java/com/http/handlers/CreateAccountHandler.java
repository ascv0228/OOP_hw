package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class CreateAccountHandler extends BaseHandler {

    public CreateAccountHandler() {
        this.path = "/createAccount";
        this.htmlPath = "createAccount.html";
        this.parameters = List.of("name", "authority", "gender");
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
        String member = SimpleHttpServer.getBaseController().createMember(params.get("name"), params.get("authority"),
                params.get("gender"));
        String response = "create a member:\n" + member;

        sendResponse(exchange, response);
    }

}
