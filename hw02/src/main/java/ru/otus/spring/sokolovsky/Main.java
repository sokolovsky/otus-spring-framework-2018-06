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
 * Опционально: сервисы, по возможности, покрыть тестами.
 *
 * Добавить файл настроек в приложение для тестирования, Annotation- + Java-based конфигурация приложения
 * Добавьте файл настроек для приложения тестирования.
 * Не забудьте добавить аналогичный файл и для тестов.
 *
 * Локализовать выводимые сообщения и вопросы теста.
 *
 * И переписать конфигурацию в виде Java + Annotation-based конфигурации.
 */

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.sokolovsky.domain.QuizIterator;
import ru.otus.spring.sokolovsky.presentation.QuizPresentation;

import java.util.Locale;

@ComponentScan
@Configuration
@PropertySource("/application/application.properties")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();

        QuizIterator quizIterator = context.getBean(QuizIterator.class);
        QuizPresentation quizPresentation = context.getBean(QuizPresentation.class);

        String locale;
        if (args.length > 0) {
            locale = args[0];
        } else {
            locale = Locale.getDefault().getLanguage();
        }
        System.setProperty("system.locale", locale);

        // it can be gone thought context but it seems to be implicitly
        quizPresentation.run(quizIterator);
    }
}
