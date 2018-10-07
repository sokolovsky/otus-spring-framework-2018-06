package ru.otus.spring.sokolovsky.hw13.services;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw13.domain.*;
import ru.otus.spring.sokolovsky.hw13.repository.AuthorRepository;
import ru.otus.spring.sokolovsky.hw13.repository.BookRepository;
import ru.otus.spring.sokolovsky.hw13.repository.GenreRepository;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public LibraryServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Book> getList() {
        return getList(null, null);
    }

    @Override
    public List<Book> getList(@Nullable String authorId, @Nullable String genreId) {
        List<Book> emptyResult = Collections.emptyList();

        Author authorEntity = null;
        if (Objects.nonNull(authorId)) {
            Optional<Author> optional = authorRepository.findById(authorId);
            if (!optional.isPresent()) {
                return emptyResult;
            }
            authorEntity = optional.get();
        }

        Genre genreEntity = null;
        if (Objects.nonNull(genreId)) {
            Optional<Genre> optional = genreRepository.findById(genreId);
            if (!optional.isPresent()) {
                return emptyResult;
            }
            genreEntity = optional.get();
        }

        if (Objects.isNull(genreEntity) && Objects.isNull(authorEntity)) {
            return bookRepository.findAll();
        }
        if (!Objects.isNull(genreEntity) && Objects.isNull(authorEntity)) {
            return bookRepository.findByGenres(genreEntity);
        }
        if (Objects.isNull(genreEntity) && !Objects.isNull(authorEntity)) {
            return bookRepository.findByAuthors(authorEntity);
        }
        if (!Objects.isNull(genreEntity) && !Objects.isNull(authorEntity)) {
            return bookRepository.findByAuthorsAndGenres(authorEntity, genreEntity);
        }
        return emptyResult;
    }

    @Override
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre registerGenre(@NotNull String title) {
        Genre genre = new Genre(title);
        genreRepository.save(genre);
        return genre;
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author registerAuthor(@NotNull String name) {
        Author author = new Author(name);
        authorRepository.save(author);
        return author;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Book registerBook(String isbn, String title) {
        Book book = new Book(isbn, title);
        bookRepository.save(book);
        return book;
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Author getAuthorById(String id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Genre getGenreById(String id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Book getBookById(String id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            throw new NotExistException();
        }
        return book.get();
    }

    @Override
    public void fillGenres(Book book, List<String> genreIds) {
        genreIds.forEach(g -> {
            Genre genre = this.getGenreById(g);
            book.addGenre(genre);
        });
    }

    @Override
    public void fillAuthors(Book book, List<String> authorIds) {
        authorIds.forEach(a -> {
            Author author = this.getAuthorById(a);
            book.addAuthor(author);
        });
    }
}
