package api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String serializeToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }
}
