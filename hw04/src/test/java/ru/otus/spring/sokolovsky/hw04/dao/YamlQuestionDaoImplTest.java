package ru.otus.spring.sokolovsky.hw04.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.sokolovsky.hw04.domain.Question;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YamlQuestionDaoImplTest {

    @Autowired
    private YamlQuestionDaoImpl dao;

    @TestConfiguration
    static class TestBeensConfiguration {
        @Bean
        @Primary
        ResourceSupplier getResourceSupplier() {
            return () -> new ClassPathResource("/test/quiz.yml");
        }
    }

    @Test
    public void countOfQuestions() {
        assertEquals(2, dao.size());
    }

    @Test
    public void questionDetail() {
        Question question = dao.getByIndex(0);
        assertEquals("first test question", question.getDescription());
        assertEquals(4, question.getVariants().length);
        assertEquals(1, question.getRightVariantNumber());
    }
}
