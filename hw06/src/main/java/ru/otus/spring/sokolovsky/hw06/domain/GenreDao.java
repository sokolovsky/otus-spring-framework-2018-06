package ru.otus.spring.sokolovsky.hw06.domain;

import java.util.List;

public interface GenreDao {
    Genre findById(long id);

    List<Genre> findAll();

    Genre findByTitle(String s);
}
