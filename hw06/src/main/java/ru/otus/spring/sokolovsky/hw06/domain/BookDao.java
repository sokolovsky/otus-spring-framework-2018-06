package ru.otus.spring.sokolovsky.hw06.domain;

import java.util.List;

public interface BookDao {
    Book findById(long id);

    Book findByIsbn(String ISBN);

    List<Book> findAll();
}
