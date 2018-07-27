package ru.otus.spring.sokolovsky.hw06.domain;

import java.util.List;

public interface GenreDao extends PersistenceManipulate<Genre> {
    Genre getById(long id);

    List<Genre> getAll();

    Genre findByTitle(String s);

    void insert(Genre entity);
}
