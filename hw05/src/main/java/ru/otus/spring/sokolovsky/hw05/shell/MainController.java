package ru.otus.spring.sokolovsky.hw05.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.sokolovsky.hw05.dao.AuthorDao;
import org.h2.tools.Console;

import java.sql.SQLException;

@ShellComponent
public class MainController {

    private final AuthorDao authorDao;

    @Autowired
    public MainController(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @ShellMethod("Descripting ")
    public void test() {
        try {
            Console.main(new String[]{});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(authorDao.getById(1).getName());
        System.out.println(authorDao.getAll());
    }
}
