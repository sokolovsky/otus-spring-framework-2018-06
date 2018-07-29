package ru.otus.spring.sokolovsky.hw06.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.sokolovsky.hw06.domain.*;
import ru.otus.spring.sokolovsky.hw06.repository.AuthorRepository;
import ru.otus.spring.sokolovsky.hw06.repository.BookRepository;
import ru.otus.spring.sokolovsky.hw06.repository.GenreRepository;

import java.util.List;
import java.util.Objects;

@ShellComponent
public class ShellController {

    private final AuthorRepository authorRepo;
    private final BookRepository bookRepo;
    private final GenreRepository genreRepo;

    @Autowired
    public ShellController(AuthorRepository authorDao, BookRepository bookDao, GenreRepository genreDao) {
        this.authorRepo = authorDao;
        this.bookRepo = bookDao;
        this.genreRepo = genreDao;
    }

    @ShellMethod("Shows list of library books. Example: books --author Пушкин --genre \"Русская проза\"")
    public String books(@ShellOption(defaultValue = ShellOption.NULL) String author, @ShellOption(defaultValue = ShellOption.NULL) String genre) {
        Author authorEntity = null;
        if (Objects.nonNull(author)) {
            authorEntity = authorRepo.findByName(author);
            if (Objects.isNull(authorEntity)) {
                return "Nothing to show";
            }
        }

        Genre genreEntity = null;
        if (Objects.nonNull(genre)) {
            genreEntity = genreRepo.findByTitle(genre);
            if (Objects.isNull(genreEntity)) {
                return "Nothing to show";
            }
        }

        List<Book> list = bookRepo.findAll(); // Переработать!!
        StringBuilder sb = new StringBuilder();
        list.forEach(s -> sb.append("\n").append(s));
        return sb.append("\n").toString();
    }

    @ShellMethod("List all genres in library without any parameters.")
    public String genres() {
        StringBuilder sb = new StringBuilder();
        genreRepo.findAll().forEach(e -> sb.append("\n").append(e));
        return sb.append("\n").toString();
    }

    @ShellMethod("List all authors in library without any parameters.")
    public String authors() {
        StringBuilder sb = new StringBuilder();
        authorRepo.findAll().forEach(e -> sb.append("\n").append(e));
        return sb.append("\n").toString();
    }

    @ShellMethod("Registers a new genre with title \"--title\"")
    public String registerGenre(@ShellOption String title) {
        Genre storedEntity = genreRepo.findByTitle(title);
        if (Objects.nonNull(storedEntity)) {
            return "Genre with the same title exits yet.";
        }
        Genre newEntity = new Genre();
        newEntity.setTitle(title);
        genreRepo.save(newEntity);
        return String.format("The new entity was registered with id %s", newEntity.getId());
    }

    @ShellMethod("Registers a new author with name \"--name\"")
    public String registerAuthor(@ShellOption String name) {
        Author storedAuthor = authorRepo.findByName(name);
        if (Objects.nonNull(storedAuthor)) {
            return "Author with the same name exists yet.";
        }
        Author newEntity = new Author();
        newEntity.setName(name);
        authorRepo.save(newEntity);
        return String.format("The new entity was registered with id %s", newEntity.getId());
    }

    @ShellMethod("Registers a new book using authors and genres ids." +
            " Example: register-book --title \"Название книги\" --ISBN \"43625-2345-34\" --author 2 --genre 1")
    public String registerBook(
            @ShellOption String title,
            @ShellOption("--ISBN") String isbn,
            @ShellOption long author,
            @ShellOption long genre) {
        Book storedBook = bookRepo.findByIsbn(isbn);
        if (Objects.nonNull(storedBook)) {
            return "Book with the same ISBN exists";
        }

        Author authorEntity = authorRepo.findById(author);
        if (Objects.isNull(authorEntity)) {
            return "Author record doesn't exist";
        }

        Genre genreEntity = genreRepo.findById(genre);
        if (Objects.isNull(genreEntity)) {
            return "Genre record doesn't exist";
        }

        Book newEntity = new Book(isbn, title);
        newEntity.addAuthor(authorEntity);
        newEntity.addGenre(genreEntity);

        bookRepo.save(newEntity);
        return String.format("The new entity was registered with id %s", newEntity.getId());
    }
}
