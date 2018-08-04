package ru.otus.spring.sokolovsky.hw06.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw06.domain.Author;
import ru.otus.spring.sokolovsky.hw06.domain.AuthorDao;

import java.util.List;

@Repository
public interface AuthorRepository extends AuthorDao, CrudRepository<Author, Long> {
    @Override
    List<Author> findAll();

    @Override
    Author findByName(String s);

    @Override
    Author save(Author entity);
    
    @Override
    void delete(Author entity);
}
