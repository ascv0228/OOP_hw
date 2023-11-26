package com.http.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public abstract class BaseHandler implements HttpHandler {
    protected String path;
    protected String htmlPath;
    private final static String htmlRootPath = "src\\main\\java\\com\\http\\public\\";
    protected String responseFormat;
    protected List<String> parameters;

    public String get_path() {
        return this.path;
    }

    public List<String> get_parameters() {
        return this.parameters == null ? List.of("") : this.parameters;
    }

    protected String sendErrorResponse() {
        System.out.println("sendErrorResponse");
        return "Necessary URL Parameter:\n" + String.join(", ", get_parameters());
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

        return params;
    }

    protected void sendHtml(HttpExchange exchange) throws IOException {
        byte[] fileBytes = readFileBytes(BaseHandler.htmlRootPath + this.htmlPath);
        sendResponse(exchange, fileBytes);
    }

    protected void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    protected void sendResponse(HttpExchange exchange, byte[] response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, response.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }

    protected static byte[] readFileBytes(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }
}
