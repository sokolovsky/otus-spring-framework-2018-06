package ru.otus.spring.sokolovsky.hw06.domain;

import java.util.List;

public interface BookDao {
    Book getById(int id);

    Book getByISBN(String ISBN);

    List<Book> getAll();

    List<Book> getByCategories(Author author, Genre genre);

    void insert(Book entity);
}
