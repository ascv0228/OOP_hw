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
    private final static String htmlRootPath = "src\\main\\java\\com\\public\\";
    protected String responseFormat;
    protected List<String> parameters;

    public String get_path() {
        return this.path;
    }

    public List<String> get_parameters() {
        return this.parameters == null ? List.of("") : this.parameters;
    }

    protected boolean checkParameter(Map<String, String> get_parameters) {
        if (this.parameters == null)
            return true;
        return get_parameters.keySet().containsAll(this.parameters);
    }

    protected boolean isApiPath(HttpExchange exchange) {
        String requestPath = exchange.getRequestURI().getPath();
        return requestPath.equals(this.path + "/api");
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

    protected void sendScriptResponse(HttpExchange exchange, String requestPath) throws IOException {
        byte[] fileBytes = readFileBytes(BaseHandler.htmlRootPath + requestPath);
        sendResponse(exchange, fileBytes, "application/javascript");
    }

    protected void sendStyleResponse(HttpExchange exchange, String requestPath) throws IOException {
        byte[] fileBytes = readFileBytes(BaseHandler.htmlRootPath + requestPath);
        sendResponse(exchange, fileBytes, "text/css");
    }

    protected void sendErrorResponse(HttpExchange exchange) throws IOException {
        System.out.println("sendErrorResponse");
        String response = "Necessary URL Parameter:\n" + String.join(", ", get_parameters());

        exchange.sendResponseHeaders(507, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    protected void sendResponse(HttpExchange exchange, byte[] response) throws IOException {
        sendResponse(exchange, response, response.length, 200, "text/html");
    }

    protected void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    protected void sendResponse(HttpExchange exchange, String response, String value) throws IOException {
        sendResponse(exchange, response.getBytes(), response.length(), 200, value);
    }

    protected void sendResponse(HttpExchange exchange, byte[] response, String value) throws IOException {
        sendResponse(exchange, response, response.length, 200, value);
    }

    protected void sendResponse(HttpExchange exchange, String response, int rCode, String value) throws IOException {
        sendResponse(exchange, response.getBytes(), response.length(), rCode, value);
    }

    protected void sendResponse(HttpExchange exchange, byte[] response, int responseLength, int rCode, String value)
            throws IOException {
        exchange.getResponseHeaders().set("Content-Type", value);
        exchange.sendResponseHeaders(rCode, responseLength);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }

    protected static byte[] readFileBytes(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    protected Map<String, String> parseCookies(HttpExchange exchange) {
        return parseCookies(exchange.getRequestHeaders().getFirst("Cookie"));
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
        exchange.getResponseHeaders().add("Set-Cookie", "userToken=" + userToken + "; Path=/");
        exchange.getResponseHeaders().add("Set-Cookie", "permission=" + fields.get("permission") + "; Path=/");
        return true;

    }
}
