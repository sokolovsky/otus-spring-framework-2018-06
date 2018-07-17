package ru.otus.spring.sokolovsky.hw04.dao;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw04.ApplicationProperties;
import ru.otus.spring.sokolovsky.hw04.LocaleCodeSource;

import java.util.Map;

@Service
public class QuestionResourceProcessor implements ResourceSupplier {

    private final ClassPathResource resource;

    public QuestionResourceProcessor(ApplicationProperties applicationProperties, LocaleCodeSource localeCodeSource) {
        Map<String, String> quizPaths = applicationProperties.getQuizPaths();
        String currentLaunchPath = quizPaths.get(localeCodeSource.getCode());
        resource = new ClassPathResource(currentLaunchPath);
    }

    @Override
    public Resource getResource() {
        return resource;
    }
}
