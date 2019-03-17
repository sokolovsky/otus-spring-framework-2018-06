package ru.otus.spring.sokolovsky.hw11.services;

import org.springframework.lang.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.domain.Author;
import ru.otus.spring.sokolovsky.hw11.domain.Book;
import ru.otus.spring.sokolovsky.hw11.domain.Genre;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface LibraryService {
    Flux<Book> getList(@Nullable String authorId, @Nullable String genreId);

    Flux<Book> getList();

    Flux<Genre> getGenres();

    Mono<Genre> registerGenre(@NotNull String name);

    Flux<Author> getAuthors();

    Mono<Author> registerAuthor(@NotNull String name);

    Mono<Book> getBookByIsbn(String isbn);

    Mono<Book> registerBook(String isbn, String title);

    Mono<Void> save(Book newEntity);

    /**
     * Return null if not present
     */
    Mono<Author> getAuthorById(String author);

    /**
     * Returns null if not present
     */
    Mono<Genre> getGenreById(String genre);

    Mono<Void> delete(Book book);

    Mono<Book> getBookById(String id);

    Mono<Void> fillGenres(Book book, List<String> genreIds);

    Mono<Void> fillAuthors(Book book, List<String> authorIds);
}
