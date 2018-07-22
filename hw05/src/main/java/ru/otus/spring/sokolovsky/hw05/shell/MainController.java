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

    @ShellMethod("List all genres in library without any parameters.")
    public String genres() {
        StringBuilder sb = new StringBuilder();
        genreDao.getAll().forEach(e -> sb.append("\n").append(e));
        return sb.append("\n").toString();
    }

    @ShellMethod("List all authors in library without any parameters.")
    public String authors() {
        StringBuilder sb = new StringBuilder();
        authorDao.getAll().forEach(e -> sb.append("\n").append(e));
        return sb.append("\n").toString();
    }

    @ShellMethod("Registers a new genre with title \"--title\"")
    public String registerGenre(@ShellOption String title) {
        Genre storedEntity = genreDao.findByTitle(title);
        if (Objects.nonNull(storedEntity)) {
            return "Genre with the same title exits yet.";
        }
        Genre newEntity = new Genre();
        newEntity.setTitle(title);
        genreDao.insert(newEntity);
        return String.format("The new entity was registered with id %s", newEntity.getId());
    }

    @ShellMethod("Registers a new author with name \"--name\"")
    public String registerAuthor(@ShellOption String name) {
        Author storedAuthor = authorDao.getByName(name);
        if (Objects.nonNull(storedAuthor)) {
            return "Author with the same name exists yet.";
        }
        Author newEntity = new Author();
        newEntity.setName(name);
        authorDao.insert(newEntity);
        return String.format("The new entity was registered with id %s", newEntity.getId());
    }
}
