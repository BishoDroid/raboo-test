package com.rabobank.betest.service;

import com.rabobank.betest.dto.TransactionRecord;
import com.rabobank.betest.dto.ValidationReport;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidationService {

    /**
     * Does the validation on each transaction based on the rules from the readme
     * @param records the transaction records
     * @return a list of {@link ValidationReport}
     */
    public List<ValidationReport> validateTransactions(List<TransactionRecord> records) {
        List<ValidationReport> reports = new ArrayList<>();
        Set<Long> uniqueReferences = new HashSet<>();

        for (TransactionRecord record : records) {
            log.debug("Validating transaction {}", record.getReference());
            List<String> errors = new ArrayList<>();

            // Check for unique transaction reference
            if (!uniqueReferences.add(record.getReference())) {
                errors.add("Duplicate transaction reference");
            }

            // Validate end balance
            if (record.getStartBalance().add(record.getMutation()).compareTo(record.getEndBalance()) != 0) {
                errors.add("Invalid end balance");
            }

            // Check for non-negative balances
            if (record.getStartBalance().compareTo(BigDecimal.ZERO) < 0
                    || record.getEndBalance().compareTo(BigDecimal.ZERO) < 0) {
                errors.add("Balance cannot be negative");
            }

            // Check for future transaction date
            if (record.getTransactionDate().after(Date.from(Instant.now()))) {
                errors.add("Transaction date cannot be in the future");
            }

            // Create a report if there are any errors
            if (!errors.isEmpty()) {
                reports.add(new ValidationReport(
                        record.getReference(), record.getDescription(), "FAILED", String.join(", ", errors)));
            }
        }
        log.info("Found {} issues in the transactions", reports.size());
        return reports;
    }
}
