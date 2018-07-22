package ru.otus.spring.sokolovsky.hw05.domain;

import java.util.List;

public interface AuthorDao {
    Author getById(int id);

    List<Author> getAll();

    Author getByName(String s);
}
