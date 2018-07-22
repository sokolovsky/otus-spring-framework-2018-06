package ru.otus.spring.sokolovsky.hw04.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.sokolovsky.hw04.accounting.UserPassage;
import ru.otus.spring.sokolovsky.hw04.accounting.UserPassageFactory;
import ru.otus.spring.sokolovsky.hw04.presentation.QuizPresentation;

@ShellComponent
public class Controller {

    private final UserPassageFactory userPassageFactory;
    private final QuizPresentation quizPresentation;

    public Controller(UserPassageFactory userPassageFactory, QuizPresentation quizPresentation) {
        this.userPassageFactory = userPassageFactory;
        this.quizPresentation = quizPresentation;
    }

    @ShellMethod("Start of quiz process. Usage:start --name Some_Name")
    public void start(@ShellOption String name) {
        UserPassage instance = userPassageFactory.getInstance(name);
        quizPresentation.run(instance);
    }
}


