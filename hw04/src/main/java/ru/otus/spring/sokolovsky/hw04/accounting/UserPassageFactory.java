package ru.otus.spring.sokolovsky.hw04.accounting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw04.domain.QuizIterator;

@Service
public class UserPassageFactory {

    private final QuizIterator quizIterator;

    @Autowired
    public UserPassageFactory(QuizIterator quizIterator) {
        this.quizIterator = quizIterator;
    }

    public UserPassage getInstance(String userName) {
        return new UserPassageImpl(userName, quizIterator);
    }
}
