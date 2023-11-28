// package com.http.handlers;

// import java.io.IOException;
// import java.util.List;
// import java.util.HashMap;
// import java.util.Map;

// import com.http.SimpleHttpServer;
// import com.sun.net.httpserver.HttpExchange;

// public final class CookieHandler extends BaseHandler {

// public CookieHandler() {
// this.path = "/cookie";
// this.htmlPath = "cookie.html";
// this.parameters = List.of("userToken");

// }

// @Override
// public void handle(HttpExchange exchange) throws IOException {
// // 处理HTTP请求

// Map<String, String> params =
// getParameters(exchange.getRequestURI().getQuery());

// String requestMethod = exchange.getRequestMethod();

// if (requestMethod.equalsIgnoreCase("GET")) {
// // Get the cookie from the request
// Map<String, String> cookies =
// parseCookies(exchange.getRequestHeaders().getFirst("Cookie"));
// if (!cookies.containsKey("userToken") && !checkParameter(params)) {
// String response = sendErrorResponse();
// sendResponse(exchange, response);
// return;
// }
// Map<String, String> fields =
// SimpleHttpServer.getBaseController().get_MemberField(params.get("userToken"));
// if (fields == null) {
// String response = sendErrorResponse();
// sendResponse(exchange, response);
// return;
// }
// exchange.getResponseHeaders().add("Set-Cookie", "userToken=" +
// params.get("userToken"));
// exchange.getResponseHeaders().add("Set-Cookie", "permission=" +
// fields.get("permission"));
// sendHtml(exchange);
// return;
// }

// // sendResponse(exchange, response);

// sendHtml(exchange);
// }

// }
