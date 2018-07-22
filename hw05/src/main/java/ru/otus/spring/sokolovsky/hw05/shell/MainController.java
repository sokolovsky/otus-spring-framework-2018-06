package ru.otus.spring.sokolovsky.hw05.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.sokolovsky.hw05.domain.*;

import java.util.List;
import java.util.Objects;

@ShellComponent
public class MainController {

    private final AuthorDao authorDao;
    private BookDao bookDao;
    private GenreDao genreDao;

    @Autowired
    public MainController(AuthorDao authorDao, BookDao bookDao, GenreDao genreDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
    }

    @ShellMethod("Shows list of library books. Example: books --author Пушкин --genre \"Русская проза\"")
    public String books(@ShellOption(defaultValue = ShellOption.NULL) String author, @ShellOption(defaultValue = ShellOption.NULL) String genre) {
        Author authorEntity = null;
        if (Objects.nonNull(author)) {
            authorEntity = authorDao.getByName(author);
            if (Objects.isNull(authorEntity)) {
                return "Nothing to show";
            }
        }

        Genre genreEntity = null;
        if (Objects.nonNull(genre)) {
            genreEntity = genreDao.findByTitle(genre);
            if (Objects.isNull(genreEntity)) {
                return "Nothing to show";
            }
        }

        List<Book> list = bookDao.getByCategories(authorEntity, genreEntity);
        StringBuilder sb = new StringBuilder();
        list.forEach(s -> sb.append("\n").append(s));
        return sb.append("\n").toString();
    }
}
