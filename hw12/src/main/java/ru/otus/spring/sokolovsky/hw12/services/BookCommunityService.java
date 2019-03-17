package ru.otus.spring.sokolovsky.hw12.services;

import ru.otus.spring.sokolovsky.hw12.domain.Book;
import ru.otus.spring.sokolovsky.hw12.domain.Comment;

public interface BookCommunityService {
    Comment registerBookComment(Book book, String comment);
}
