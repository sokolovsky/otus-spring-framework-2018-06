package ru.otus.spring.sokolovsky.hw11.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.domain.Book;
import ru.otus.spring.sokolovsky.hw11.domain.Comment;
import ru.otus.spring.sokolovsky.hw11.repository.BookRepository;
import ru.otus.spring.sokolovsky.hw11.repository.CommentRepository;

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
    public Mono<Comment> registerBookComment(Book book, String comment) {
        Objects.requireNonNull(book);
        Comment toRegisterComment = book.addComment(comment);
        Mono<Comment> commentMono = commentRepository.save(toRegisterComment);
        Mono<Book> bookMono = bookRepository.save(book);

        Flux<Comment> flux = Flux.from(commentMono);
        flux.then(bookMono);
        return flux.single();
    }
}
