package ru.otus.spring.sokolovsky.hw12.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw12.domain.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {

    List<Author> findAll();

    Author findByName(String s);
}
