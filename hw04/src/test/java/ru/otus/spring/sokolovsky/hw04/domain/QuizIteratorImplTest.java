package ru.otus.spring.sokolovsky.hw04.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.sokolovsky.hw04.dao.QuestionDao;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuizIteratorImplTest {

    @Autowired
    private QuizIterator quizIterator;

    @TestConfiguration
    static class Configuration {
        @Bean
        @Primary
        public QuestionDao dao() {
            return new QuestionDao() {

                @Override
                public Question getByIndex(int index) {
                    return new Question("" + index, 1, new String[]{""});
                }

                @Override
                public int size() {
                    return 3;
                }
            };
        }
    }

    @Before
    public void prepare() {
        quizIterator.reset();
    }

    @Test
    public void firstQuestion() {
        assertEquals("0", quizIterator.getCurrentQuestion().getDescription());
    }

    @Test
    public void iterateQuestions() {
        quizIterator.next();
        quizIterator.next();
        assertEquals("2", quizIterator.getCurrentQuestion().getDescription());
    }

    @Test
    public void runOutQuestions() {
        quizIterator.next();
        quizIterator.next();
        quizIterator.next();
        assertFalse(quizIterator.hasNext());
    }
}