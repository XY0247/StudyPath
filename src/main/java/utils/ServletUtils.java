package utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtils {
    public static void writeJsonResponse(HttpServletResponse resp, String key, String value) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(String.format("{\"%s\": \"%s\"}", key, value));
        }
    }
}
