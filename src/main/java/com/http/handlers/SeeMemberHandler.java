package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class SeeMemberHandler extends BaseHandler {

    public SeeMemberHandler() {
        this.path = "/seeMember";
        this.htmlPath = "seeMember.html";
        this.parameters = List.of("userToken");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 处理HTTP请求

        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        if (params.size() == 0) {
            sendHtml(exchange);
            return;
        }
        if (!checkParameter(params)) {
            String response = sendErrorResponse();
            sendResponse(exchange, response);
            return;
        }
        String member = SimpleHttpServer.getBaseController().get_MemberInfo(params.get("userToken"));
        String response = "MemberInfo:\n" + member;

        sendResponse(exchange, response);
    }

}
