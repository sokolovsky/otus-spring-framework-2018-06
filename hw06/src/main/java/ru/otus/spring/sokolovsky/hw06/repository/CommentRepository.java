package ru.otus.spring.sokolovsky.hw06.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw06.domain.Comment;
import ru.otus.spring.sokolovsky.hw06.domain.CommentDao;

import java.util.List;

@Repository
public interface CommentRepository extends CommentDao, CrudRepository<Comment, Long> {
    List<Comment> findByText(String text);
}
