package com.rabobank.betest.util;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.rabobank.betest.exception.AppException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

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
            throw new AppException(
                    "Error while processing csv file. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
