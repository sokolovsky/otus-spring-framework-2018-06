package ru.otus.spring.sokolovsky.hw05.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.sokolovsky.hw05.domain.AuthorDao;
import org.h2.tools.Console;
import ru.otus.spring.sokolovsky.hw05.domain.BookDao;

import java.sql.SQLException;

@ShellComponent
public class MainController {

    private final AuthorDao authorDao;
    private BookDao bookDao;

    @Autowired
    public MainController(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    @ShellMethod("Descripting ")
    public void test() {
        System.out.println(authorDao.getById(1).getName());

        System.out.println(bookDao.getAll());
    }
}
