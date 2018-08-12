package ru.otus.spring.sokolovsky.hw07.services;

import ru.otus.spring.sokolovsky.hw07.domain.Book;
import ru.otus.spring.sokolovsky.hw07.domain.Comment;

public interface BookCommunityService {
    Comment registerBookComment(Book book, String comment);
}
