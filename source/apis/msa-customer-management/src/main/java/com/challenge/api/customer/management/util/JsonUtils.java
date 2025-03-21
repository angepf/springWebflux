package com.challenge.api.customer.management.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public static <T> T convertJsonToObject(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            log.error("Error converting JSON to object: {}", e.getMessage(), e);
            throw new JsonConversionException("Error converting JSON to object", e);
        }
    }

    public static class JsonConversionException extends RuntimeException {
        public JsonConversionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}