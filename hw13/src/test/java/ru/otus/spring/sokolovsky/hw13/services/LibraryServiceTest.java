package ru.otus.spring.sokolovsky.hw13.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.sokolovsky.hw13.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw13.domain.Book;
import ru.otus.spring.sokolovsky.hw13.repository.AuthorRepository;
import ru.otus.spring.sokolovsky.hw13.repository.BookRepository;
import ru.otus.spring.sokolovsky.hw13.repository.GenreRepository;

import static org.hamcrest.Matchers.nullValue;
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
        assertEquals(service.getList(null, null).size(), bookRepository.findAll().size());
        assertEquals(3, service.getList(authorRepository.findByName("Фёдор Тютчев").getId(), null).size());
        assertEquals(1, service.getList(null, genreRepository.findByTitle("Классическая проза").getId()).size());
        assertEquals(1, service.getList(
                    authorRepository.findByName("Александр Пушкин").getId(),
                    genreRepository.findByTitle("Классическая проза").getId()
                ).size()
        );
    }

    @Test
    void getGenres() {
        assertTrue(service.getGenres().size() > 0);
    }

    @Test
    void registerGenre() {
        int before = service.getGenres().size();
        service.registerGenre("New model");
        assertEquals(before + 1, service.getGenres().size());
    }

    @Test
    void getAuthors() {
        assertTrue(service.getAuthors().size() > 0);
    }

    @Test
    void registerAuthor() {
        service.registerAuthor("New noweller");
        assertEquals(4, service.getAuthors().size());
    }

    @Test
    void getBookByIsbn() {
        assertNotNull(service.getBookByIsbn(isbn));
    }

    @Test
    void registerBook() {
        Book book = service.registerBook("003535-9988", "The new title of book.");
        assertNotEquals(nullValue(), book.getId());
    }
}