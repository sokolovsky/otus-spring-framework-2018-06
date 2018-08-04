package ru.otus.spring.sokolovsky.hw06.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw06.domain.Genre;
import ru.otus.spring.sokolovsky.hw06.domain.GenreDao;

import java.util.List;

@Repository
public interface GenreRepository extends GenreDao, CrudRepository<Genre, Long> {
    @Override
    List<Genre> findAll();

    @Override
    Genre findByTitle(String s);

    @Override
    Genre save(Genre entity);
}
