package ru.otus.spring.sokolovsky.hw05.dao;


import ru.otus.spring.sokolovsky.hw05.domain.Genre;

import java.util.List;

interface GenreDao {
    Genre getById(int id);

    List<Genre> getAll();
}
