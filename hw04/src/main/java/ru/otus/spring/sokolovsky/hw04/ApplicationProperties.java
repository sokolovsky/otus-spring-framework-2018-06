package ru.otus.spring.sokolovsky.hw04;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("application")
public class ApplicationProperties {

    private final Map<String, String> quizPaths = new HashMap<>();
    private String localeBundlePath;

    public Map<String, String> getQuizPaths() {
        return quizPaths;
    }

    public void setLocaleBundlePath(String path) {
        localeBundlePath = path;
    }

    public String getLocaleBundlePath() {
        return localeBundlePath;
    }
}
