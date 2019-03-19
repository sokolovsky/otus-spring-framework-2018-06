package ru.otus.spring.sokolovsky.hw13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw13.domain.Author;
import ru.otus.spring.sokolovsky.hw13.domain.Book;
import ru.otus.spring.sokolovsky.hw13.domain.Genre;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Book findByIsbn(String isbn);

    List<Book> findAll();

    List<Book> findByAuthors(Author author);

    List<Book> findByGenres(Genre genre);

    List<Book> findByAuthorsAndGenres(Author author, Genre genre);
}
