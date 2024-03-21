package com.rabobank.betest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationReport {

    private long reference;
    private String description;
    private String status;
    private String message;
}
