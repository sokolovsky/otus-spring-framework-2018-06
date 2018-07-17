package ru.otus.spring.sokolovsky.hw03.presentation;

import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw03.domain.Question;
import ru.otus.spring.sokolovsky.hw03.domain.QuizIterator;
import ru.otus.spring.sokolovsky.hw03.localization.Locale;

import java.io.PrintStream;
import java.io.Reader;
import java.util.Scanner;

@Service
public class CliQuizPresentation implements QuizPresentation {

    private final Reader in;
    private final PrintStream out;
    private Locale locale;

    public CliQuizPresentation(Reader in, PrintStream out, Locale locale) {
        this.in = in;
        this.out = out;
        this.locale = locale;
    }

    @Override
    public void run(QuizIterator quizIterator) {
        out.println(locale.message("greeting"));
        String person = readLine();
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
                out.println(locale.message("incorrectInput"));
                continue;
            }
            if (answerVariant == question.getRightVariantNumber()) {
                score++;
            }
            quizIterator.next();
        }
        out.println(locale.message("total", new String[] {person, "" + score}));
    }

    private String readLine() {
        Scanner scanner = new Scanner(in);
        return scanner.nextLine();
    }
}
