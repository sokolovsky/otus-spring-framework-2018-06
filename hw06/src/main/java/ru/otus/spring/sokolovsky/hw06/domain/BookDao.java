package ru.otus.spring.sokolovsky.hw06.domain;

import java.util.List;

public interface BookDao extends PersistenceManipulate<Book> {
    Book getById(int id);

    Book getByISBN(String ISBN);

    List<Book> getAll();

    List<Book> getByCategories(Author author, Genre genre);
}
