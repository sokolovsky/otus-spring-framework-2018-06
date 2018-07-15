package ru.otus.spring.sokolovsky.hw03.dao;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw03.ApplicationProperties;
import ru.otus.spring.sokolovsky.hw03.LocaleCodeSource;
import ru.otus.spring.sokolovsky.hw03.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository("questionDao")
public class QuestionDaoImpl implements QuestionDao {

    private List<Question> questions = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public QuestionDaoImpl(ApplicationProperties applicationProperties, LocaleCodeSource localeCodeSource) {
        Map<String, String> quizPaths = applicationProperties.getQuizPaths();
        String currentLaunchPath = quizPaths.get(localeCodeSource.getCode());

        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource(currentLaunchPath));
        List<Object> list = (List<Object>) Objects.requireNonNull(yamlMapFactoryBean.getObject()).get("questions");
        for (Object questionMap : list) {
            register((Map<String, Object>) questionMap);
        }
    }

    private void register(Map<String, Object> map) {
        Question question = new Question(
                (String) map.get("description"),
                (int) map.get("right-variant-index"),
                (String[]) ((List) map.get("variants")).toArray(new String[0])
        );
        questions.add(question);
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
