package ru.otus.spring.sokolovsky.hw08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw08.domain.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {

    Genre findById(long id);

    List<Genre> findAll();

    Genre findByTitle(String s);
}
