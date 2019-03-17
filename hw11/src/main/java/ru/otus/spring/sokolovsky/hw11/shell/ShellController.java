package ru.otus.spring.sokolovsky.hw11.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.sokolovsky.hw11.changelogs.SeedCreator;

@ShellComponent
public class ShellController {

    private final SeedCreator seedCreator;

    @Autowired
    public ShellController(SeedCreator seedCreator) {
        this.seedCreator = seedCreator;
    }

    @ShellMethod("Initializes book library for application working.")
    public String libraryInitialize() {
        seedCreator.create();
        return "Library was successfully initialized.";
    }
}
