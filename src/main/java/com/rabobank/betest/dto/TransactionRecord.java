package com.rabobank.betest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "TransactionRecord")
public class TransactionRecord {

    @JsonProperty("Reference")
    @CsvBindByName(column = "Reference")
    @JacksonXmlProperty(localName = "reference")
    private long reference;

    @JsonProperty("Account Number")
    @CsvBindByName(column = "Account Number")
    @JacksonXmlProperty(localName = "accountNumber")
    private String accountNumber;

    @JsonProperty("Start Balance")
    @CsvBindByName(column = "Start Balance")
    @JacksonXmlProperty(localName = "startBalance")
    private BigDecimal startBalance;

    @JsonProperty("Mutation")
    @CsvBindByName(column = "Mutation")
    @JacksonXmlProperty(localName = "mutation")
    private BigDecimal mutation;

    @JsonProperty("Description")
    @CsvBindByName(column = "Description")
    @JacksonXmlProperty(localName = "description")
    private String description;

    @JsonProperty("End Balance")
    @CsvBindByName(column = "End Balance")
    @JacksonXmlProperty(localName = "endBalance")
    private BigDecimal endBalance;

    @JsonProperty("Transaction Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @CsvBindByName(column = "Transaction Date")
    @CsvDate("yyyy-MM-dd")
    @JacksonXmlProperty(localName = "date")
    private Date transactionDate;
}
