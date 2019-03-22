package ru.otus.spring.sokolovsky.hw13.services;

import org.springframework.security.core.Authentication;
import ru.otus.spring.sokolovsky.hw13.domain.Book;

public interface AccessTestService {

    boolean canEditBook(Authentication authentication, Book book);

    boolean canDeleteBook(Authentication authentication, Book book);

    boolean canAddBook(Authentication authentication);

    boolean canLeaveBookComment(Authentication authentication, Book book);
}
