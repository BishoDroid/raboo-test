package com.rabobank.betest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> convert(InputStream inputStream, Class<T> type) {
        try {
            return objectMapper.readValue(
                    inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            log.error("Error processing json file {}", e.getMessage());
            throw new RuntimeException("Error processing JSON file", e);
        }
    }
}
