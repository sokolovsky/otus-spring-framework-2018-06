package ru.otus.spring.sokolovsky.hw05.domain;

import java.util.List;

public interface BookDao {
    Book getById(int id);

    Book getByISBN(String ISBN);

    List<Book> getByAuthor(int authorId);

    List<Book> getByAuthor(Author author);

    List<Book> getByGenre(int genreId);

    List<Book> getByGenre(Genre genre);

    List<Book> getAll();
}
