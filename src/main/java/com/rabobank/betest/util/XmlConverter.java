package com.rabobank.betest.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.InputStream;
import java.util.List;

public class XmlConverter {

    private static final XmlMapper xmlMapper = new XmlMapper();

    public static <T> List<T> convert(InputStream inputStream, Class<T> type) {
        try {
            return xmlMapper.readValue(
                    inputStream, xmlMapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (Exception e) {
            throw new RuntimeException("Error processing XML file", e);
        }
    }
}
