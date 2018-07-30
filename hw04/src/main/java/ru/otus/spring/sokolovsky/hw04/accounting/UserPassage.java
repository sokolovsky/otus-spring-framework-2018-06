package ru.otus.spring.sokolovsky.hw04.accounting;

import ru.otus.spring.sokolovsky.hw04.domain.Question;

public interface UserPassage {
    String getName();

    String getCurrentQuestion();

    String[] getVariants();

    void next();

    boolean hasNext();

    void setAnswer(int variant);

    int getCount();

    int getRights();
}
