package ru.otus.spring.sokolovsky;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.sokolovsky.domain.QuizIterator;
import ru.otus.spring.sokolovsky.presentation.QuizPresentation;

@ComponentScan
@Configuration
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();

        QuizIterator quizIterator = context.getBean(QuizIterator.class);
        QuizPresentation quizPresentation = context.getBean(QuizPresentation.class);

        // it can be gone thought context but it seems to be implicitly
        quizPresentation.run(quizIterator);
    }
}
