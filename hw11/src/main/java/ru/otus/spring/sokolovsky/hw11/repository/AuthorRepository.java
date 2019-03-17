package ru.otus.spring.sokolovsky.hw11.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.domain.Author;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {

    Flux<Author> findAll();

    Mono<Author> findByName(String s);
}
