package ru.otus.spring.sokolovsky.hw06.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw06.domain.Author;
import ru.otus.spring.sokolovsky.hw06.domain.AuthorDao;

import java.util.List;

@Repository
public class AuthorRepository extends BaseJpaRepository<Author> implements AuthorDao {
    @Override
    public Author getById(long id) {
        return null;
    }

    @Override
    public List<Author> getAll() {
        return null;
    }

    @Override
    public Author getByName(String s) {
        return null;
    }

    @Override
    public void save(Author entity) {

    }

    @Override
    public void delete(Author entity) {

    }
}
