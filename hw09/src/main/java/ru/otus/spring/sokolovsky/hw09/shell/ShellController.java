package ru.otus.spring.sokolovsky.hw09.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.sokolovsky.hw09.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw09.domain.*;
import ru.otus.spring.sokolovsky.hw09.services.BookCommunityService;
import ru.otus.spring.sokolovsky.hw09.services.LibraryService;
import ru.otus.spring.sokolovsky.hw09.services.NotExistException;

import java.util.List;
import java.util.Objects;

@ShellComponent
public class ShellController {

    private final LibraryService libraryService;
    private final BookCommunityService bookCommunityService;
    private final SeedCreator seedCreator;

    @Autowired
    public ShellController(LibraryService bookService, BookCommunityService bookCommunityService, SeedCreator seedCreator) {
        this.libraryService = bookService;
        this.bookCommunityService = bookCommunityService;
        this.seedCreator = seedCreator;
    }

    @ShellMethod("Shows list of library books. Example: books --author Пушкин --genre \"Русская проза\"")
    public String books(@ShellOption(defaultValue = ShellOption.NULL) String author, @ShellOption(defaultValue = ShellOption.NULL) String genre) {
        List<Book> list = libraryService.getList(author, genre);
        StringBuilder sb = new StringBuilder();
        list.forEach(s -> sb.append("\n").append(s));
        return sb.append("\n").toString();
    }

    @ShellMethod("List all genres in library without any parameters.")
    public String genres() {
        StringBuilder sb = new StringBuilder();
        libraryService.getGenres().forEach(e -> sb.append("\n").append(e));
        return sb.append("\n").toString();
    }

    @ShellMethod("List all authors in library without any parameters.")
    public String authors() {
        StringBuilder sb = new StringBuilder();
        libraryService.getAuthors().forEach(e -> sb.append("\n").append(e));
        return sb.append("\n").toString();
    }

    @ShellMethod("Registers a new genre with title \"--title\"")
    public String registerGenre(@ShellOption String title) {
        Genre genre = libraryService.registerGenre(title);
        return String.format("The new entity was registered with id %s", genre.getId());
    }

    @ShellMethod("Registers a new author with name \"--name\"")
    public String registerAuthor(@ShellOption String name) {
        Author author = libraryService.registerAuthor(name);
        return String.format("The new entity was registered with id %s", author.getId());
    }

    @ShellMethod("Registers a new book using authors and genres ids." +
            " Example: register-book --title \"Название книги\" --ISBN \"43625-2345-34\" --author 2 --genre 1")
    public String registerBook(
            @ShellOption String title,
            @ShellOption("--ISBN") String isbn,
            @ShellOption String author,
            @ShellOption String genre) {
        Book storedBook = libraryService.getBookByIsbn(isbn);
        if (Objects.nonNull(storedBook)) {
            return "Book with the same ISBN exists";
        }

        Author authorEntity = libraryService.getAuthorById(author);
        if (Objects.isNull(authorEntity)) {
            return "Author record doesn't exist";
        }

        Genre genreEntity = libraryService.getGenreById(genre);
        if (Objects.isNull(genreEntity)) {
            return "Genre record doesn't exist";
        }

        Book newBook = libraryService.registerBook(isbn, title);
        newBook.addAuthor(authorEntity);
        newBook.addGenre(genreEntity);
        libraryService.save(newBook);
        return String.format("The new entity was registered with id %s", newBook.getId());
    }

    @ShellMethod("Registers a new comment to book." +
            " Example: add-book-comment --isbn \"038653-34534\" --comment \"Книга действительно хорошая, но есть одно НО\"")
    public String addBookComment(@ShellOption String isbn, @ShellOption String comment) {
        Book book = libraryService.getBookByIsbn(isbn);
        Comment commentEntity = bookCommunityService.registerBookComment(book, comment);
        return String.format("Comment was added: %s", commentEntity.toString());
    }

    @ShellMethod("Shows book information.")
    public String bookInfo(@ShellOption String isbn) {
        Book book;
        try {
            book = libraryService.getBookByIsbn(isbn);
        } catch (NotExistException e) {
            return String.format("Book with isbn %s not exist", isbn);
        }
        return Objects.nonNull(book) ? book.toString() : "Info is empty";
    }

    @ShellMethod("Initializes book library for application working.")
    public String libraryInitialize() {
        seedCreator.create();
        return "Library was successfully initialized.";
    }
}
