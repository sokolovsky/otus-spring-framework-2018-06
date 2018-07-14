package ru.otus.spring.sokolovsky.dao;

import ru.otus.spring.sokolovsky.domain.Question;

public interface QuestionDao {
    Question getByIndex(int index);

    int size();
}
