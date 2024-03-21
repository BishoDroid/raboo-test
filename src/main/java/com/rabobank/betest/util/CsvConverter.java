package com.rabobank.betest.util;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsvConverter {

    public static <T> List<T> convert(InputStream inputStream, Class<T> type) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();

            strategy.setType(type);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV file", e);
        }
    }
}
