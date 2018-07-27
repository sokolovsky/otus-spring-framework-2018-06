package ru.otus.spring.sokolovsky.hw06.domain;

public interface PersistenceManipulate<E extends BaseEntity> {
    void save(E entity);

    void delete(E entity);
}
