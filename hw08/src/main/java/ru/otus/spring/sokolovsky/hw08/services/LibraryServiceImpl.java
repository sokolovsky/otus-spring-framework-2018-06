package ru.otus.spring.sokolovsky.hw08.services;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.sokolovsky.hw08.domain.*;
import ru.otus.spring.sokolovsky.hw08.repository.AuthorRepository;
import ru.otus.spring.sokolovsky.hw08.repository.BookRepository;
import ru.otus.spring.sokolovsky.hw08.repository.GenreRepository;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public LibraryServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genregenreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genregenreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getList(@Nullable String author, @Nullable String genre) {
        List<Book> emptyResult = Collections.emptyList();

        Author authorEntity = null;
        if (Objects.nonNull(author)) {
            authorEntity = authorRepository.findByName(author);
            if (Objects.isNull(authorEntity)) {
                return emptyResult;
            }
        }

        Genre genreEntity = null;
        if (Objects.nonNull(genre)) {
            genreEntity = genreRepository.findByTitle(genre);
            if (Objects.isNull(genreEntity)) {
                return emptyResult;
            }
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
    public Author getAuthorById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Genre getGenreById(long id) {
        return genreRepository.findById(id);
    }
}
