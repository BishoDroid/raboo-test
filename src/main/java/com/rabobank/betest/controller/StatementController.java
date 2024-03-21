package com.rabobank.betest.controller;

import com.rabobank.betest.dto.TransactionRecord;
import com.rabobank.betest.dto.ValidationReport;
import com.rabobank.betest.service.StatementsProcessingFactory;
import com.rabobank.betest.service.ValidationService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statements")
public class StatementController {

    private final ValidationService validationService;
    private final StatementsProcessingFactory statementsProcessingFactory;

    @PostMapping("/validate")
    public ResponseEntity<?> validateStatements(
            @RequestBody String body, @RequestHeader("Content-Type") String contentType) {
        List<TransactionRecord> records = statementsProcessingFactory.processRequestBody(body, contentType);
        List<ValidationReport> reports = validationService.validateTransactions(records);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PostMapping("/validate/file")
    public ResponseEntity<?> validateStatementsFile(
            @RequestParam("file") MultipartFile file, @RequestParam("contentType") String contentType)
            throws IOException {

        List<TransactionRecord> records = statementsProcessingFactory.processFile(file, contentType);
        List<ValidationReport> reports = validationService.validateTransactions(records);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
}
