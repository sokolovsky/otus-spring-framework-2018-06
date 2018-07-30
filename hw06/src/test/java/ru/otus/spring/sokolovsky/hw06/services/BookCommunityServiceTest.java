package ru.otus.spring.sokolovsky.hw06.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.sokolovsky.hw06.domain.Book;
import ru.otus.spring.sokolovsky.hw06.domain.Comment;
import ru.otus.spring.sokolovsky.hw06.repository.CommentRepository;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@Transactional
public class BookCommunityServiceTest {

    final String isbn = "978-5-9905833-8-2";

    @Autowired
    private BookCommunityService service;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void registerComment() {
        Book book = libraryService.getBookByIsbn(isbn);
        Comment comment = service.registerBookComment(book, "Test comment");

        assertNotEquals(0, comment.getId());

        List<Comment> commentsWithTestText = commentRepository.findByText("Test comment");
        boolean isPersist = commentsWithTestText.stream().anyMatch(c -> c.getId() == comment.getId());
        assertTrue(isPersist);
    }
}