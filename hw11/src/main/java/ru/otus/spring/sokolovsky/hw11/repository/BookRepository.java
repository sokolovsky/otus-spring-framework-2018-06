package ru.otus.spring.sokolovsky.hw11.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.domain.Author;
import ru.otus.spring.sokolovsky.hw11.domain.Book;
import ru.otus.spring.sokolovsky.hw11.domain.Genre;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, String> {
    Mono<Book> findByIsbn(String isbn);

    Flux<Book> findAll();

    Flux<Book> findByAuthors(Author author);

    Flux<Book> findByGenres(Genre genre);

    Flux<Book> findByAuthorsAndGenres(Author author, Genre genre);
}
