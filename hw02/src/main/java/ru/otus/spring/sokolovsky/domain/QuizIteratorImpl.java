package ru.otus.spring.sokolovsky.domain;

import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.dao.QuestionDao;

@Service
public class QuizIteratorImpl implements QuizIterator {

    private QuestionDao dao;
    private int pointer = 0;

    public QuizIteratorImpl(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public Question getCurrentQuestion() {
        return dao.getByIndex(pointer);
    }

    @Override
    public void next() {
        if (!hasNext()) {
            throw new RuntimeException("Next element is not exist");
        }
        pointer++;
    }

    @Override
    public boolean hasNext() {
        return dao.size() > pointer + 1;
    }
}
