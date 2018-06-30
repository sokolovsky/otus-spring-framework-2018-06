package ru.otus.spring.sokolovsky.domain;

public interface QuizIterator {

    Question getCurrentQuestion();

    void next();

    boolean hasNext();
}
