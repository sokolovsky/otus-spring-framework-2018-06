package ru.otus.spring.sokolovsky.hw08.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw08.domain.Author;
import ru.otus.spring.sokolovsky.hw08.domain.Book;
import ru.otus.spring.sokolovsky.hw08.domain.Genre;

import java.util.List;

// todo Redefine queries from JPA to mongo requests
@Repository
public interface BookRepository extends CrudRepository<Book, String> {
    Book findByIsbn(String isbn);

    List<Book> findAll();

    @Query("select b from Book b inner join b.authors a where a = ?1")
    List<Book> findByAuthor(Author author);

    @Query("select b from Book b inner join b.genres g where g = ?1")
    List<Book> findByGenre(Genre genre);

    @Query("select b from Book b inner join b.authors a inner join b.genres g where a = ?1 and g = ?2")
    List<Book> findByAuthorAndGenre(Author author, Genre genre);
}
