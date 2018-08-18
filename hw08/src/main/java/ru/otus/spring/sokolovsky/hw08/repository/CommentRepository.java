package ru.otus.spring.sokolovsky.hw08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw08.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByText(String text);
}
