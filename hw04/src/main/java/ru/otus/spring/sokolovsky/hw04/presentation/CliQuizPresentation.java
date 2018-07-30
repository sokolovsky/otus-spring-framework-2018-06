package ru.otus.spring.sokolovsky.hw04.presentation;

import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw04.accounting.UserPassage;
import ru.otus.spring.sokolovsky.hw04.localization.Locale;

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
    public void run(UserPassage userPassage) {
        out.println(locale.message("greeting", userPassage.getName()));
        while (userPassage.hasNext()) {
            out.println(userPassage.getCurrentQuestion());
            int index = 1;
            for (String variant: userPassage.getVariants()) {
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
            userPassage.setAnswer(answerVariant);
            userPassage.next();
        }
        out.println(locale.message("total", userPassage.getName(), "" + userPassage.getRights()));
    }

    private String readLine() {
        Scanner scanner = new Scanner(in);
        return scanner.nextLine();
    }
}
