package com.rabobank.betest.util;

import com.rabobank.betest.exception.AppException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;

public enum RequestType {
    CSV("text/csv"),
    JSON("application/json"),
    XML("application/xml");

    private final String contentType;

    RequestType(String contentType) {
        this.contentType = contentType;
    }

    public static RequestType fromString(String type) {
        return Arrays.stream(RequestType.values())
                .filter(t -> type.equalsIgnoreCase(t.contentType))
                .findFirst()
                .orElseThrow(() -> new AppException(
                        "Invalid request content type. Allowed values are application/xml, application/json, and text/csv",
                        HttpStatus.BAD_REQUEST));
    }
}
