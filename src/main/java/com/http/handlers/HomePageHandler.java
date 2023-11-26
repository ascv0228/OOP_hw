package com.http.handlers;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;

public final class HomePageHandler extends BaseHandler {

    public HomePageHandler() {
        this.path = "/";
        this.htmlPath = "home.html";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        sendHtml(exchange);
    }

}
