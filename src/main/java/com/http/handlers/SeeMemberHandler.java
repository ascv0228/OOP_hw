package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class SeeMemberHandler extends BaseHandler {

    public SeeMemberHandler() {
        this.path = "/seeMember";
        this.parameters = List.of("userToken");
        this.htmlPath = "seeMember.html";
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
        String member = SimpleHttpServer.getBaseController().get_MemberInfo(params.get("userToken"));
        String response = member;

        sendResponse(exchange, response);
    }

}
