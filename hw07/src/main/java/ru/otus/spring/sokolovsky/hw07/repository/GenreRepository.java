package ru.otus.spring.sokolovsky.hw07.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw07.domain.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends DomainRepository<Genre, Long> {

    Genre findById(long id);

    List<Genre> findAll();

    Genre findByTitle(String s);
}
