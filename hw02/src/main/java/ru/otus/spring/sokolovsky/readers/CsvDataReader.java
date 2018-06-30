package ru.otus.spring.sokolovsky.readers;

import org.springframework.stereotype.Service;

@Service
public class CsvDataReader implements DataReader {

    public CsvDataReader(String sourcePath) {

    }

    public String[] getNext() {
        return new String[0];
    }
}
