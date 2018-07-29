package ru.otus.spring.sokolovsky.hw06.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw06.domain.Book;
import ru.otus.spring.sokolovsky.hw06.domain.BookDao;

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
}
