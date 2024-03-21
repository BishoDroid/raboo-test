package com.rabobank.betest.service;

import com.rabobank.betest.dto.TransactionRecord;
import com.rabobank.betest.exception.AppException;
import com.rabobank.betest.util.CsvConverter;
import com.rabobank.betest.util.JsonConverter;
import com.rabobank.betest.util.RequestType;
import com.rabobank.betest.util.XmlConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class StatementsProcessingFactory {

    /**
     * Process the records file that contains the transactions to be validated and converts them to POJO
     * @param file the file containing the records
     * @param type the file type (JSON, XML or CSV)
     * @return a list of {@link TransactionRecord}
     * @throws IOException when reading file
     */
    public List<TransactionRecord> processFile(MultipartFile file, String type) throws IOException {
        if (file.isEmpty()) {
            throw new AppException("You must upload a file containing the transactions", HttpStatus.BAD_REQUEST);
        }

        RequestType fileType = RequestType.fromString(type);
        log.info("Processing file {} using {} converter", file.getOriginalFilename(), fileType);
        return convert(file.getInputStream(), fileType);
    }

    /**
     * Processes the request body as JSON, XML or CSV and converts them to POJO
     * @param requestBody the request
     * @param type content type
     * @return a list of {@link TransactionRecord}s
     */
    public List<TransactionRecord> processRequestBody(String requestBody, String type) {
        if (requestBody.isEmpty()) {
            throw new AppException("Request body containing the transactions is empty", HttpStatus.BAD_REQUEST);
        }

        RequestType requestType = RequestType.fromString(type);
        log.info("Processing request body using {} converter", requestType);
        InputStream inputStream = new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8));
        return convert(inputStream, requestType);
    }

    private List<TransactionRecord> convert(InputStream inputStream, RequestType requestType) {
        return switch (requestType) {
            case CSV -> CsvConverter.convert(inputStream, TransactionRecord.class);
            case JSON -> JsonConverter.convert(inputStream, TransactionRecord.class);
            case XML -> XmlConverter.convert(inputStream, TransactionRecord.class);
        };
    }
}
