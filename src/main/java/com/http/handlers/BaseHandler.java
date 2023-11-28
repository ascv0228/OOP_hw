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
import com.http.SimpleHttpServer;
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

    // Parse the cookies from the request
    protected Map<String, String> parseCookies(String cookieHeader) {
        System.out.println(cookieHeader);
        Map<String, String> cookies = new HashMap<>();

        if (cookieHeader != null) {
            String[] cookieArray = cookieHeader.split("; ");
            for (String cookie : cookieArray) {
                String[] parts = cookie.split("=");
                if (parts.length == 2) {
                    cookies.put(parts[0], parts[1]);
                }
            }
        }

        return cookies;
    }

    protected boolean hasCorrectCookie(HttpExchange exchange) {
        Map<String, String> cookies = parseCookies(exchange.getRequestHeaders().getFirst("Cookie"));
        return cookies.containsKey("userToken") && cookies.containsKey("permission");
    }

    protected boolean setCookie(HttpExchange exchange) {

        Map<String, String> params = getParameters(exchange.getRequestURI().getQuery());
        // Get the cookie from the request
        if (!hasCorrectCookie(exchange) && !params.containsKey("userToken")) {
            return false;
        }
        return setCookie(exchange, params.get("userToken"));

    }

    protected boolean setCookie(HttpExchange exchange, String userToken) {

        Map<String, String> fields = SimpleHttpServer.getBaseController().get_MemberField(userToken);
        if (fields == null) {
            System.out.println("Error userToken: " + userToken);
            return false;
        }
        exchange.getResponseHeaders().add("Set-Cookie", "userToken=" + userToken);
        exchange.getResponseHeaders().add("Set-Cookie", "permission=" + fields.get("permission"));
        return true;

    }
}
