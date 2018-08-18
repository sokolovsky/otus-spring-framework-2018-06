package ru.otus.spring.sokolovsky.hw09.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw09.domain.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {

    Author findById(long id);

    List<Author> findAll();

    Author findByName(String s);
}
