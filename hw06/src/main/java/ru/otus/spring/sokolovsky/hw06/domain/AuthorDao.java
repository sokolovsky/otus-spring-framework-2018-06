package ru.otus.spring.sokolovsky.hw06.domain;

import java.util.List;

public interface AuthorDao {
    Author findById(long id);

    List<Author> findAll();

    Author findByName(String s);
}
