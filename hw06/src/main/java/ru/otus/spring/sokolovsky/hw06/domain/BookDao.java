package ru.otus.spring.sokolovsky.hw06.domain;

import java.util.List;

public interface BookDao extends Manipulation<Book> {
    Book findById(long id);

    Book findByIsbn(String ISBN);

    List<Book> findAll();

    List<Book> findByGenre(Genre genre);

    List<Book> findByAuthor(Author author);

    List<Book> findByAuthorAndGenre(Author author, Genre genre);
}
