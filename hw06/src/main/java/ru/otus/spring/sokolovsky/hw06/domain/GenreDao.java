package ru.otus.spring.sokolovsky.hw06.domain;

import java.util.List;

public interface GenreDao {
    Genre getById(long id);

    List<Genre> getAll();

    Genre findByTitle(String s);

    void insert(Genre entity);
}
