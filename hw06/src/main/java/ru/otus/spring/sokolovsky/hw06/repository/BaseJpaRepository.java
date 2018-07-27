package ru.otus.spring.sokolovsky.hw06.repository;

import ru.otus.spring.sokolovsky.hw06.domain.BaseEntity;
import ru.otus.spring.sokolovsky.hw06.domain.PersistenceManipulate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseJpaRepository<E extends BaseEntity> implements PersistenceManipulate<E> {

    @PersistenceContext
    private EntityManager em;

    EntityManager getEm() {
        return em;
    }

    @Override
    public void save(E entity) {
        if (entity.getId() == 0) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
    }

    @Override
    public void delete(E entity) {
        em.remove(entity);
    }
}
