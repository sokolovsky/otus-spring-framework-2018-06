package ru.otus.spring.sokolovsky.hw04.dao;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw04.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository("questionDao")
public class YamlQuestionDaoImpl implements QuestionDao {

    private List<Question> questions = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public YamlQuestionDaoImpl(ResourceSupplier resourceSupplier) {
        List<Object> list = getRawListOfQuestions(resourceSupplier.getResource());
        for (Object questionMap : list) {
            register((Map<String, Object>) questionMap);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Object> getRawListOfQuestions(Resource resource) {
        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        yamlMapFactoryBean.setResources(resource);
        return (List<Object>) Objects.requireNonNull(yamlMapFactoryBean.getObject()).get("questions");
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
