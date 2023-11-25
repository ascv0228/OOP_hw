package com.http.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public abstract class BaseHandler implements HttpHandler {
    protected String path;
    protected String responseFormat;
    protected List<String> parameters;
    protected Map<String, String> options;

    public String get_path() {
        return this.path;
    }

    public List<String> get_parameters() {
        return this.parameters == null ? List.of("") : this.parameters;
    }

    public Map<String, String> get_options() {
        return this.options == null ? new HashMap<>() : this.options;
    }

    protected String sendErrorResponse() {
        System.out.println("sendErrorResponse");
        return "Necessary URL Parameter:\n" + String.join(", ", get_parameters())
                + "\n\n" + "Optional URL Parameter:\n" + String.join(", ", new ArrayList<>(get_options().keySet()));
    }

    protected boolean checkParameter(Map<String, String> get_parameters) {
        if (this.parameters == null)
            return true;
        return get_parameters.keySet().containsAll(this.parameters);
    }

    @Override
    public abstract void handle(HttpExchange exchange) throws IOException;

    protected Map<String, String> getParameters(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length > 1) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    params.put(key, value);
                }
            }
        }
        if (this.options != null)
            for (String key : this.options.keySet()) {
                params.putIfAbsent(key, this.options.get(key));
            }
        return params;
    }

    protected void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
