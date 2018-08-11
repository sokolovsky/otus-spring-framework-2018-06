package ru.otus.spring.sokolovsky.hw08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw08.domain.Author;
import ru.otus.spring.sokolovsky.hw08.domain.Book;
import ru.otus.spring.sokolovsky.hw08.domain.Genre;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Book findByIsbn(String isbn);

    List<Book> findAll();

    @Query("{'author': ?0}")
    List<Book> findByAuthor(Author author);

    @Query("{'genre': ?0}")
    List<Book> findByGenre(Genre genre);

    @Query("{'author': ?0, 'genre': ?1}")
    List<Book> findByAuthorAndGenre(Author author, Genre genre);
}
