package ru.otus.spring.sokolovsky.hw11.services;

import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.domain.Book;
import ru.otus.spring.sokolovsky.hw11.domain.Comment;

public interface BookCommunityService {
    Mono<Comment> registerBookComment(Book book, String comment);
}
