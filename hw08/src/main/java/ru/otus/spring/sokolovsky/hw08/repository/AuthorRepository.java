package ru.otus.spring.sokolovsky.hw08.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw08.domain.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, String> {

    Author findById(long id);

    List<Author> findAll();

    Author findByName(String s);
}
