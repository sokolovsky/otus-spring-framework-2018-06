package ru.otus.spring.sokolovsky.hw11.web;

import java.util.Map;

public class IllegalPostDataException extends RuntimeException {
    private Map<String, Object> errors;

    public IllegalPostDataException(Map<String, Object> errors) {
        this.errors = errors;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }
}
