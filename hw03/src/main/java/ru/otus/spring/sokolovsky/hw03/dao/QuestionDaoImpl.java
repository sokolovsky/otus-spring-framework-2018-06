package ru.otus.spring.sokolovsky.hw03.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw03.ApplicationProperties;
import ru.otus.spring.sokolovsky.hw03.LocaleCodeSource;
import ru.otus.spring.sokolovsky.hw03.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("questionDao")
public class QuestionDaoImpl implements QuestionDao {

    private List<Question> questions = new ArrayList<>();

    public QuestionDaoImpl(ApplicationProperties applicationProperties, LocaleCodeSource localeCodeSource) {
        Map<String, String> quizPaths = applicationProperties.getQuizPaths();
        String currentLaunchPath = quizPaths.get(localeCodeSource.getCode());

        // получение пути к ямл файлу для
    }

    @Override
    public Question getByIndex(int index) {
        return questions.get(index);
    }

    @Override
    public int size() {
        return questions.size();
    }
}
