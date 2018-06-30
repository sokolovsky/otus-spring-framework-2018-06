package ru.otus.spring.sokolovsky.presentation;

import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.domain.Question;
import ru.otus.spring.sokolovsky.domain.QuizIterator;

import java.io.*;
import java.util.Scanner;

@Service
public class CliQuizPresentation implements QuizPresentation {

    private final Reader in;
    private final PrintStream out;

    private String person;

    public CliQuizPresentation(Reader in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run(QuizIterator quizIterator) {
        // доработать слой локализации
        out.println("Приложение опрос. Введите ваше имя:");
        person = readLine();
        int score = 0;
        while (quizIterator.hasNext()) {
            Question question = quizIterator.getCurrentQuestion();
            out.println(question.getDescription());
            int index = 1;
            for (String variant: question.getVariants()) {
                out.println(index + " - " + variant);
                index++;
            }
            String answer = readLine();
            int answerVariant;
            try {
                answerVariant = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                continue;
            }
            if (answerVariant == question.getRightVariantNumber()) {
                score++;
            }
            quizIterator.next();
        }
        out.println(person + ", опрос окончен, сумма ваших баллов: " + score);
    }

    private String readLine() {
        Scanner scanner = new Scanner(in);
        return scanner.nextLine();
    }
}
