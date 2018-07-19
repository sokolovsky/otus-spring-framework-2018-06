package ru.otus.spring.sokolovsky.hw05.dao;

import ru.otus.spring.sokolovsky.hw05.domain.Author;
import ru.otus.spring.sokolovsky.hw05.domain.Book;
import ru.otus.spring.sokolovsky.hw05.domain.Genre;

import java.util.List;

interface BookDao {
    Book getById(int id);

    Book getByISBN(String ISBN);

    List<Book> getByAuthor(int authorId);

    List<Book> getByAuthor(Author author);

    List<Book> getByGenre(int genreId);

    List<Book> getByGenre(Genre genre);

    List<Book> getAll();
}
