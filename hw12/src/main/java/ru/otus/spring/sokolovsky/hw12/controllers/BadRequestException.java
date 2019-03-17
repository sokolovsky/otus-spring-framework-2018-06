package ru.otus.spring.sokolovsky.hw12.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    BadRequestException(String message) {
        super(message);
    }
}
