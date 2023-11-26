package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class LoginAccountHandler extends BaseHandler {

    public LoginAccountHandler() {
        this.path = "/loginAccount";
        this.htmlPath = "loginAccount.html";
        this.parameters = List.of("userToken");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        if (params.size() == 0) {
            sendHtml(exchange);
        }
        if (!checkParameter(params)) {
            String response = sendErrorResponse();
            sendResponse(exchange, response);
        }
        String member = SimpleHttpServer.getBaseController().get_LoginAccount(params.get("userToken"));
        String response = "Login member:\n" + member;

        sendResponse(exchange, response);
    }

}
