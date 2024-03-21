package com.rabobank.betest.service;

import com.rabobank.betest.dto.TransactionRecord;
import com.rabobank.betest.dto.ValidationReport;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ValidationServiceTest {

    @Test
    public void testValidateStatements_AllValid() {
        ValidationService service = new ValidationService();
        List<TransactionRecord> records = List.of(
                createRecord(1, "NL93ABNA0585619023", "100.00", "50.00", "Payment", "150.00", 1),
                createRecord(2, "NL93ABNA0585619024", "200.00", "-50.00", "Withdrawal", "150.00", 2));

        List<ValidationReport> reports = service.validateTransactions(records);
        Assertions.assertTrue(reports.isEmpty());
    }

    @Test
    public void testValidateStatements_InvalidEndBalance() {
        ValidationService service = new ValidationService();
        List<TransactionRecord> records =
                List.of(createRecord(1, "NL93ABNA0585619023", "100.00", "50.00", "Payment", "140.00", 1));

        List<ValidationReport> reports = service.validateTransactions(records);
        Assertions.assertFalse(reports.isEmpty());
        Assertions.assertEquals("Invalid end balance", reports.get(0).getMessage());
    }

    @Test
    public void testValidateStatements_DuplicateReference() {
        ValidationService service = new ValidationService();
        List<TransactionRecord> records = List.of(
                createRecord(1, "NL93ABNA0585619023", "100.00", "50.00", "Payment", "150.00", 1),
                createRecord(1, "NL93ABNA0585619024", "200.00", "-50.00", "Withdrawal", "150.00", 2));

        List<ValidationReport> reports = service.validateTransactions(records);
        Assertions.assertFalse(reports.isEmpty());
        Assertions.assertEquals(
                "Duplicate transaction reference", reports.get(0).getMessage());
    }

    private TransactionRecord createRecord(
            long reference,
            String accountNumber,
            String startBalance,
            String mutation,
            String description,
            String endBalance,
            int daysAgo) {
        return new TransactionRecord(
                reference,
                accountNumber,
                new BigDecimal(startBalance),
                new BigDecimal(mutation),
                description,
                new BigDecimal(endBalance),
                Date.from(Instant.now().minus(daysAgo, ChronoUnit.DAYS)));
    }
}
