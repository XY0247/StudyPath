package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 读取请求体中的 JSON 并将其转换为 Java 对象
     *
     * @param req   HttpServletRequest 请求对象
     * @param clazz 需要转换的类
     * @param <T>   泛型类型
     * @return 转换后的 Java 对象
     * @throws IOException 发生 IO 异常时抛出
     */
    public static <T> T parseJsonRequest(HttpServletRequest req, Class<T> clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return objectMapper.readValue(sb.toString(), clazz);
    }

    /**
     * 读取请求体中的 JSON 并将其转换为 JsonNode 对象
     *
     * @param req   HttpServletRequest 请求对象
     * @return 包含 json 数据的 JsonNode
     * @throws IOException 发生 IO 异常时抛出
     */
    public static JsonNode parseJsonRequest(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return objectMapper.readTree(sb.toString());
    }

    /**
     * 创建 JSON 响应并发送，带有自定义 HTTP 状态码
     *
     * @param resp       HttpServletResponse 响应对象
     * @param message    响应的消息
     * @param url        可选的重定向 URL
     * @param statusCode HTTP 状态码
     * @throws IOException 发生 IO 异常时抛出
     */
    public static void sendJsonResponse(HttpServletResponse resp, String message, String url, int statusCode) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(statusCode);

        ObjectNode jsonResponse = objectMapper.createObjectNode();
        jsonResponse.put("message", message);
        if (url != null && !url.isEmpty()) {
            jsonResponse.put("url", url);
        }

        try (PrintWriter out = resp.getWriter()) {
            out.write(objectMapper.writeValueAsString(jsonResponse));
            out.flush();
        }
    }

    /**
     * 创建成功的 JSON 响应（200 OK）
     *
     * @param resp    HttpServletResponse 响应对象
     * @param message 成功的消息
     * @param url     可选的重定向 URL
     * @throws IOException 发生 IO 异常时抛出
     */
    public static void sendSuccessResponse(HttpServletResponse resp, String message, String url) throws IOException {
        sendJsonResponse(resp, message, url, HttpServletResponse.SC_OK);
    }

    /**
     * 创建失败的 JSON 响应（如 400 Bad Request 或 500 Internal Server Error）
     *
     * @param resp       HttpServletResponse 响应对象
     * @param message    失败的消息
     * @param statusCode HTTP 状态码
     * @throws IOException 发生 IO 异常时抛出
     */
    public static void sendErrorResponse(HttpServletResponse resp, String message, int statusCode) throws IOException {
        sendJsonResponse(resp, message, null, statusCode);
    }

    /**
     * 创建通用的 JSON 响应，包含自定义数据
     *
     * @param resp       HttpServletResponse 响应对象
     * @param message    响应的消息
     * @param data       可选的附加数据
     * @param statusCode HTTP 状态码
     * @throws IOException 发生 IO 异常时抛出
     */
    public static void sendJsonResponseWithData(HttpServletResponse resp, String message, Object data, int statusCode) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(statusCode);

        ObjectNode jsonResponse = objectMapper.createObjectNode();
        jsonResponse.put("message", message);
        if (data != null) {
            jsonResponse.set("data", objectMapper.valueToTree(data));
        }

        try (PrintWriter out = resp.getWriter()) {
            out.write(objectMapper.writeValueAsString(jsonResponse));
            out.flush();
        }
    }

    public static ObjectNode getObjectNode()  {

        return objectMapper.createObjectNode();
    }

    public static void sendJsonResponse(HttpServletResponse resp, ObjectNode jsonResponse) throws IOException {
        try {
            // 设置响应内容类型和字符编码
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_OK);

            // 将JSON对象转换为字符串
            String jsonStr = objectMapper.writeValueAsString(jsonResponse);

            // 写入响应体
            try (PrintWriter out = resp.getWriter()) {
                out.write(jsonStr);
                out.flush();
            }
        } catch (Exception e) {
            // 记录异常
            // 设置响应状态码为500
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");

            // 写入错误消息
            try (PrintWriter out = resp.getWriter()) {
                out.write("Internal Server Error: " + e.getMessage());
                out.flush();
            }
        }
    }
}
