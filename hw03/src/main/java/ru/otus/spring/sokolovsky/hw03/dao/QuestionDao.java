package ru.otus.spring.sokolovsky.hw03.dao;

import ru.otus.spring.sokolovsky.hw03.domain.Question;

public interface QuestionDao {
    Question getByIndex(int index);

    int size();
}
