package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class CreateAccountHandler extends BaseHandler {

    public CreateAccountHandler() {
        this.path = "/createAccount";
        this.parameters = List.of("name", "authority", "gender");
        this.htmlPath = "createAccount.html";
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
        Pair<String, String> pair = SimpleHttpServer.getBaseController().createMember(params.get("name"),
                params.get("authority"),
                params.get("gender"));
        setCookie(exchange, pair.getLeft());
        String response = pair.getRight();

        sendResponse(exchange, response);
    }

}
