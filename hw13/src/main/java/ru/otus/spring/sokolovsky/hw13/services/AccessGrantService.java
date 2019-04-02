package ru.otus.spring.sokolovsky.hw13.services;

public interface AccessGrantService {
    AccessGrantTransaction getAccessGrantTransaction();

    void grantAccess(AccessGrantTransaction transaction);
}
