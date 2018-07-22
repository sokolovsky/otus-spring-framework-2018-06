package ru.otus.spring.sokolovsky.hw05.domain;

import java.util.List;

public interface GenreDao {
    Genre getById(int id);

    List<Genre> getAll();

    Genre findByTitle(String s);

    void insert(Genre entity);
}
