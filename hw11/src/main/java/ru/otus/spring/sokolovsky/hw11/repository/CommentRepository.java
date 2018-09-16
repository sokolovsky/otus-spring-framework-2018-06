package ru.otus.spring.sokolovsky.hw11.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.spring.sokolovsky.hw11.domain.Comment;

@Repository
public interface CommentRepository extends ReactiveCrudRepository<Comment, String> {
    Flux<Comment> findByText(String text);
}
