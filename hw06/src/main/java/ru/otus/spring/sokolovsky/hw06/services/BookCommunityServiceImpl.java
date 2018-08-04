package ru.otus.spring.sokolovsky.hw06.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw06.domain.Book;
import ru.otus.spring.sokolovsky.hw06.domain.BookDao;
import ru.otus.spring.sokolovsky.hw06.domain.Comment;
import ru.otus.spring.sokolovsky.hw06.domain.CommentDao;

import java.util.Objects;

@Service
public class BookCommunityServiceImpl implements BookCommunityService {

    private final BookDao bookDao;
    private final CommentDao commentDao;

    public BookCommunityServiceImpl(BookDao bookDao, CommentDao commentDao) {
        this.bookDao = bookDao;
        this.commentDao = commentDao;
    }

    @Override
    public Comment registerBookComment(Book book, String comment) {
        Objects.requireNonNull(book);
        Comment toRegisterComment = book.addComment(comment);
        bookDao.save(book);
        commentDao.save(toRegisterComment);
        return toRegisterComment;
    }
}
