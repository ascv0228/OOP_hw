package com.http.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class CreateAccountHandler extends BaseHandler {

    public CreateAccountHandler() {
        this.path = "/createAccount";
        this.parameters = List.of("name");
        this.options = new HashMap<String, String>() {
            {
                put("authority", "admin");
                put("gender", "Male");
            }
        };
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 处理HTTP请求
        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
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
