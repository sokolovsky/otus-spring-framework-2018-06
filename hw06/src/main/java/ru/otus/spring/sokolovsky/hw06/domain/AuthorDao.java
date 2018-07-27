package ru.otus.spring.sokolovsky.hw06.domain;

import java.util.List;

public interface AuthorDao extends PersistenceManipulate<Author> {
    Author getById(long id);

    List<Author> getAll();

    Author getByName(String s);
}
