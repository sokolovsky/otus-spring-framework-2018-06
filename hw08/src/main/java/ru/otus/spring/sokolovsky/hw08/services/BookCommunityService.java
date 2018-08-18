package ru.otus.spring.sokolovsky.hw08.services;

import ru.otus.spring.sokolovsky.hw08.domain.Book;
import ru.otus.spring.sokolovsky.hw08.domain.Comment;

public interface BookCommunityService {
    Comment registerBookComment(Book book, String comment);
}
