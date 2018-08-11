package ru.otus.spring.sokolovsky.hw08.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw08.domain.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends CrudRepository<Genre, String> {

    Genre findById(long id);

    List<Genre> findAll();

    Genre findByTitle(String s);
}
