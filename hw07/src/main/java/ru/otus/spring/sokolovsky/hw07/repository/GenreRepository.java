package ru.otus.spring.sokolovsky.hw07.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw07.domain.Genre;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {

    Genre findByTitle(String s);
}
