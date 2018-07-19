package ru.otus.spring.sokolovsky.hw05.dao;

import ru.otus.spring.sokolovsky.hw05.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(int id);

    List<Author> getAll();
}
