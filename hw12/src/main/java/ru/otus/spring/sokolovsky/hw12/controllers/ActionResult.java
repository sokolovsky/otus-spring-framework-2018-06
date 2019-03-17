package ru.otus.spring.sokolovsky.hw12.controllers;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
public class ActionResult {

    @Getter
    private final boolean success;

    @Getter
    private final String message;

    @Getter
    private Map<String, Object> data;

    private ActionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static ActionResult ok(String message) {
        return new ActionResult(true, message);
    }

    public static ActionResult ok() {
        return ok("");
    }

    public static ActionResult error(String message) {
        return new ActionResult(false, message);
    }

    public void data(Map<String, Object> value) {
        this.data = value;
    }
}
