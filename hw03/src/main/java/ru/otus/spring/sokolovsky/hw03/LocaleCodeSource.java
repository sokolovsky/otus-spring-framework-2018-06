package ru.otus.spring.sokolovsky.hw03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LocaleCodeSource {
    private String code;

    private static Set<String> supportedLangs = new HashSet<String>() {{
        add("ru");
        add("en");
    }};

    @Autowired
    public LocaleCodeSource(ApplicationArguments arguments) {
        String args[] = arguments.getSourceArgs();
        if (args.length > 0) {
            code = args[0];
        } else {
            code = getDefaultLanguage();
        }

        if (!supportedLangs.contains(code)) {
            throw new RuntimeException("Language with code " + code + " is not supported");
        }
    }

    private String getDefaultLanguage() {
        return java.util.Locale.getDefault().getLanguage();
    }

    public String getCode() {
        return code;
    }
}
