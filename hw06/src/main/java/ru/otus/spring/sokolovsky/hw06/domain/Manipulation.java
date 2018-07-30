package ru.otus.spring.sokolovsky.hw06.domain;

public interface Manipulation<E> {
    E save(E entity);

    void delete(E entity);
}
