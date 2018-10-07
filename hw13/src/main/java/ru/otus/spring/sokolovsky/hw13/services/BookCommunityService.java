package ru.otus.spring.sokolovsky.hw13.services;

import ru.otus.spring.sokolovsky.hw13.domain.Book;
import ru.otus.spring.sokolovsky.hw13.domain.Comment;

public interface BookCommunityService {
    Comment registerBookComment(Book book, String comment);
}
