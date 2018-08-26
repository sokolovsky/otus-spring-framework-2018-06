package ru.otus.spring.sokolovsky.hw10.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.sokolovsky.hw10.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw10.domain.Book;
import ru.otus.spring.sokolovsky.hw10.repository.BookRepository;

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
    private BookRepository bookRepository;


    @BeforeEach
    void createFixtures(@Autowired SeedCreator seed) {
        seed.create();
    }

    @Test
    void getList() {
        assertEquals(service.getList(null, null).size(), bookRepository.findAll().size());
        assertEquals(3, service.getList("Фёдор Тютчев", null).size());
        assertEquals(1, service.getList(null, "Классическая проза").size());
        assertEquals(1, service.getList("Александр Пушкин", "Классическая проза").size());
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