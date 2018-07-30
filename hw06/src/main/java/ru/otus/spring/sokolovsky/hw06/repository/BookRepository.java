package ru.otus.spring.sokolovsky.hw06.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw06.domain.Author;
import ru.otus.spring.sokolovsky.hw06.domain.Book;
import ru.otus.spring.sokolovsky.hw06.domain.BookDao;
import ru.otus.spring.sokolovsky.hw06.domain.Genre;

import java.util.List;

@Repository
public interface BookRepository extends BookDao, CrudRepository<Book, Long> {
    @Override
    Book findByIsbn(String isbn);

    @Override
    List<Book> findAll();

    @Override
    Book save(Book entity);

    @Override
    void delete(Book entity);

    @Query("select b from Book b inner join b.authors a where a = ?1")
    List<Book> findByAuthor(Author author);

    @Query("select b from Book b inner join b.genres g where g = ?1")
    List<Book> findByGenre(Genre genre);

    @Query("select b from Book b inner join b.authors a inner join b.genres g where a = ?1 and g = ?2")
    List<Book> findByAuthorAndGenre(Author author, Genre genre);
}
