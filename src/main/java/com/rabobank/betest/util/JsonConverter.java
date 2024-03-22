package com.rabobank.betest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.betest.exception.AppException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> convert(InputStream inputStream, Class<T> type) {
        try {
            return objectMapper.readValue(
                    inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            throw new AppException("Error processing JSON file" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
