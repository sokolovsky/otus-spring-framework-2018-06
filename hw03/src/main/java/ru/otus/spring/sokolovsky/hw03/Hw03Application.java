package ru.otus.spring.sokolovsky.hw03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.sokolovsky.hw03.domain.QuizIterator;
import ru.otus.spring.sokolovsky.hw03.presentation.QuizPresentation;

@SpringBootApplication
@ComponentScan
@EnableConfigurationProperties(ApplicationProperties.class)
public class Hw03Application {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Hw03Application.class, args);
        QuizPresentation quizPresentation = context.getBean(QuizPresentation.class);
        QuizIterator quizIterator = context.getBean(QuizIterator.class);

        quizPresentation.run(quizIterator);
	}
}
