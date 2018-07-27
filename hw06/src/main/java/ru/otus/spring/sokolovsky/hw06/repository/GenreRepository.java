package ru.otus.spring.sokolovsky.hw06.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw06.domain.Genre;
import ru.otus.spring.sokolovsky.hw06.domain.GenreDao;

import java.util.List;

@Repository
public class GenreRepository extends BaseJpaRepository<Genre> implements GenreDao {
    @Override
    public Genre getById(long id) {
        return null;
    }

    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public Genre findByTitle(String s) {
        return null;
    }

    @Override
    public void insert(Genre entity) {

    }
}
