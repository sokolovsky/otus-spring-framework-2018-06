package ru.otus.spring.sokolovsky.hw04.dao;

import ru.otus.spring.sokolovsky.hw04.domain.Question;

public interface QuestionDao {
    Question getByIndex(int index);

    int size();
}
