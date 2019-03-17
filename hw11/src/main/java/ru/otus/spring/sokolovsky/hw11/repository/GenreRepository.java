package ru.otus.spring.sokolovsky.hw11.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.domain.Genre;

@Repository
public interface GenreRepository extends ReactiveCrudRepository<Genre, String> {

    Flux<Genre> findAll();

    Mono<Genre> findByTitle(String s);
}
