package ru.otus.spring.sokolovsky;

/**
 * Программа по проведению тестирования студентов
 * В ресурсах хранятся вопросы и различные ответы к ним в виде CSV файла (5 вопрсов).
 * Программа должна спросить у пользователя фамилию и имя,
 * спросить 5 вопросов из CSV-файла и вывести результат тестирования.
 *
 * Все сервисы в программе должны решать строго определённую задачу.
 * Зависимости должны быть настроены в IoC контейнере.
 *
 *
 * Локализовать выводимые сообщения и вопросы теста.
 *
 * И переписать конфигурацию в виде Java + Annotation-based конфигурации.
 */

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring.sokolovsky.domain.QuizIterator;
import ru.otus.spring.sokolovsky.presentation.QuizPresentation;

import java.util.Locale;

@PropertySource("/application/application.properties")
@ComponentScan
@Configuration
public class Main {
    /**
     * @param args The first arg is local (ru,en)
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);

        String locale;
        if (args.length > 0) {
            locale = args[0];
        } else {
            locale = Locale.getDefault().getLanguage();
        }
        System.setProperty("instance.locale", locale);
        context.refresh();

        QuizIterator quizIterator = context.getBean(QuizIterator.class);
        QuizPresentation quizPresentation = context.getBean(QuizPresentation.class);

        // it can be gone thought context but it seems to be implicitly
        quizPresentation.run(quizIterator);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
