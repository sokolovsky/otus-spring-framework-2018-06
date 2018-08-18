package ru.otus.spring.sokolovsky.hw08.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw08.domain.Book;
import ru.otus.spring.sokolovsky.hw08.domain.Comment;
import ru.otus.spring.sokolovsky.hw08.repository.BookRepository;
import ru.otus.spring.sokolovsky.hw08.repository.CommentRepository;

import java.util.Objects;

@Service
public class BookCommunityServiceImpl implements BookCommunityService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public BookCommunityServiceImpl(BookRepository bookRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment registerBookComment(Book book, String comment) {
        Objects.requireNonNull(book);
        Comment toRegisterComment = book.addComment(comment);
        bookRepository.save(book);
        commentRepository.save(toRegisterComment);
        return toRegisterComment;
    }
}
