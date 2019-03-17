package ru.otus.spring.sokolovsky.hw11.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.sokolovsky.hw11.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw11.domain.Book;
import ru.otus.spring.sokolovsky.hw11.repository.AuthorRepository;
import ru.otus.spring.sokolovsky.hw11.repository.BookRepository;
import ru.otus.spring.sokolovsky.hw11.repository.GenreRepository;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"/test-application.properties"})
class LibraryServiceTest {

    final String isbn = "978-5-9905833-8-2";

    @Autowired
    private LibraryService service;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void createFixtures(@Autowired SeedCreator seed) {
        seed.create();
    }

    @Test
    void getList() {
        assertEquals(service.getList(null, null).collectList().block().size(), bookRepository.findAll().collectList().block().size());
        assertEquals(3, service.getList(authorRepository.findByName("Фёдор Тютчев").block().getId(), null).collectList().block().size());
        assertEquals(1, service.getList(null, genreRepository.findByTitle("Классическая проза").block().getId()).collectList().block().size());
        assertEquals(1, service.getList(
                    authorRepository.findByName("Александр Пушкин").block().getId(),
                    genreRepository.findByTitle("Классическая проза").block().getId()
                ).collectList().block().size()
        );
    }

    @Test
    void getGenres() {
        assertTrue(service.getGenres().collectList().block().size() > 0);
    }

    @Test
    void registerGenre() {
        int before = service.getGenres().collectList().block().size();
        service.registerGenre("New model").block();
        assertEquals(before + 1, service.getGenres().collectList().block().size());
    }

    @Test
    void getAuthors() {
        assertTrue(service.getAuthors().collectList().block().size() > 0);
    }

    @Test
    void registerAuthor() {
        service.registerAuthor("New noweller").block();
        assertEquals(4, service.getAuthors().collectList().block().size());
    }

    @Test
    void getBookByIsbn() {
        assertNotNull(service.getBookByIsbn(isbn));
    }

    @Test
    void registerBook() {
        Book book = service.registerBook("003535-9988", "The new title of book.").block();
        assertNotEquals(nullValue(), book.getId());
    }
}
