package ru.otus.spring.sokolovsky.hw07.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class DomainRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements DomainRepository<T, ID>  {

    private final EntityManager entityManager;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DomainRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public void clear() {
        entityManager.clear();
    }
}
