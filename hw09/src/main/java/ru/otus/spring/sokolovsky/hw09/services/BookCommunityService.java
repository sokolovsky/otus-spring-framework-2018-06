package ru.otus.spring.sokolovsky.hw09.services;

import ru.otus.spring.sokolovsky.hw09.domain.Book;
import ru.otus.spring.sokolovsky.hw09.domain.Comment;

public interface BookCommunityService {
    Comment registerBookComment(Book book, String comment);
}
