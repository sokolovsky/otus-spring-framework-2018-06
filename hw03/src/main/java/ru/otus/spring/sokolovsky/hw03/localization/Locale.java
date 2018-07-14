package ru.otus.spring.sokolovsky.hw03.localization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class Locale {
    private final String code;
    private MessageSource messageSource;
    private java.util.Locale nativeLocale;

    public Locale(@Value("#{systemProperties['instance.locale']}") String code, MessageSource messageSource) {
        this.code = code;
        this.messageSource = messageSource;

        switch (code) {
            case "en":
                nativeLocale = java.util.Locale.ENGLISH;
                break;
            case "ru":
                nativeLocale = new java.util.Locale("ru", "RU");
                break;
            default:
                throw new RuntimeException("Locale for language " + code + " is not supported");
        }
    }

    public String getCode() {
        return this.code;
    }

    public String message(String s, Object[] objects) {
        return messageSource.getMessage(s, objects, nativeLocale);
    }

    public String message(String s) {
        return message(s, new Object[0]);
    }
}
