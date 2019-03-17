package ru.otus.spring.sokolovsky.hw11.services;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.domain.*;
import ru.otus.spring.sokolovsky.hw11.repository.AuthorRepository;
import ru.otus.spring.sokolovsky.hw11.repository.BookRepository;
import ru.otus.spring.sokolovsky.hw11.repository.GenreRepository;

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
    public Flux<Book> getList() {
        return getList(null, null);
    }

    @Override
    public Flux<Book> getList(@Nullable String authorId, @Nullable String genreId) {
        List<Book> emptyResult = Collections.emptyList();

        Author authorEntity = null;
        if (Objects.nonNull(authorId)) {
            Optional<Author> optional = authorRepository.findById(authorId).blockOptional();
            if (!optional.isPresent()) {
                return Flux.fromIterable(emptyResult);
            }
            authorEntity = optional.get();
        }

        Genre genreEntity = null;
        if (Objects.nonNull(genreId)) {
            Optional<Genre> optional = genreRepository.findById(genreId).blockOptional();
            if (!optional.isPresent()) {
                return Flux.fromIterable(emptyResult);
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
        return Flux.fromIterable(emptyResult);
    }

    @Override
    public Flux<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Mono<Genre> registerGenre(@NotNull String title) {
        Genre genre = new Genre(title);
        return genreRepository.save(genre);
    }

    @Override
    public Flux<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> registerAuthor(@NotNull String name) {
        Author author = new Author(name);
        return authorRepository.save(author);
    }

    @Override
    public Mono<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Mono<Book> registerBook(String isbn, String title) {
        Book book = new Book(isbn, title);
        return bookRepository.save(book);
    }

    @Override
    public Mono<Void> save(Book book) {
        return bookRepository.save(book).then();
    }

    @Override
    public Mono<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public Mono<Genre> getGenreById(String id) {
        return genreRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(Book book) {
        return bookRepository.delete(book);
    }

    @Override
    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id).doOnEach(s -> {
            if (s.get() == null) {
                throw new NotExistException();
            }
        });
    }

    @Override
    public Mono<Void> fillGenres(Book book, List<String> genreIds) {
        return Flux.fromIterable(genreIds).doOnEach(signal -> {
            Genre genre = this.getGenreById(signal.get()).block();
            book.addGenre(genre);
        }).then();
    }

    @Override
    public Mono<Void> fillAuthors(Book book, List<String> authorIds) {
        return Flux.fromIterable(authorIds).doOnEach(signal -> {
            Author author = this.getAuthorById(signal.get()).block();
            book.addAuthor(author);
        }).then();
    }
}
