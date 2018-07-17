package ru.otus.spring.sokolovsky.hw04.accounting;

import ru.otus.spring.sokolovsky.hw04.domain.Question;
import ru.otus.spring.sokolovsky.hw04.domain.QuizIterator;

public class UserPassageImpl implements UserPassage {

    private String name;
    private QuizIterator quizIterator;
    private int count = 0;
    private int rights = 0;

    public UserPassageImpl(String name, QuizIterator quizIterator) {
        this.name = name;
        this.quizIterator = quizIterator;
        this.quizIterator.reset();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCurrentQuestion() {
        return quiestion().getDescription();
    }

    @Override
    public String[] getVariants() {
        return quiestion().getVariants();
    }

    @Override
    public void next() {
        quizIterator.next();
    }

    @Override
    public boolean hasNext() {
        return quizIterator.hasNext();
    }

    @Override
    public void setAnswer(int variant) {
        count++;
        if (variant == quiestion().getRightVariantNumber()) {
            rights++;
        }
    }

    private Question quiestion() {
        return quizIterator.getCurrentQuestion();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int getRights() {
        return rights;
    }
}
