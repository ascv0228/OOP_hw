
package com.http.handlers;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.http.SimpleHttpServer;
import com.sun.net.httpserver.HttpExchange;

public final class LogoutHandler extends BaseHandler {

    public LogoutHandler() {
        this.path = "/logout";

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("AAAA");

        String requestMethod = exchange.getRequestMethod();

        // Get the cookie from the request

        exchange.getResponseHeaders().add("Set-Cookie",
                "userToken=" + "; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/");
        exchange.getResponseHeaders().add("Set-Cookie",
                "permission=" + "; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/");
        sendResponse(exchange, "Success");
    }

}
