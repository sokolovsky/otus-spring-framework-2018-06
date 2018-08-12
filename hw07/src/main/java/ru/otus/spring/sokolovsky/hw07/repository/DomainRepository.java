package ru.otus.spring.sokolovsky.hw07.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DomainRepository<T, ID> extends CrudRepository<T, ID> {
    void clear();
}
