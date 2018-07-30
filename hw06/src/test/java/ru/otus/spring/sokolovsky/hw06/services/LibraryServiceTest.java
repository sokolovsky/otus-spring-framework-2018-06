package ru.otus.spring.sokolovsky.hw06.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.sokolovsky.hw06.domain.Book;
import ru.otus.spring.sokolovsky.hw06.repository.BookRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LibraryServiceTest {

    final String isbn = "978-5-9905833-8-2";

    @Autowired
    private LibraryService service;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void getList() {
        assertEquals(service.getList(null, null).size(), bookRepository.findAll().size());
        assertEquals(2, service.getList("Фёдор Тютчев", null).size());
        assertEquals(3, service.getList(null, "Классическая проза").size());
        assertEquals(1, service.getList("Александр Пушкин", "Классическая проза").size());
    }

    @Test
    public void getGenres() {
        assertTrue(service.getGenres().size() > 0);
    }

    @Test
    public void registerGenre() {
        int before = service.getGenres().size();
        service.registerGenre("New genre");
        assertEquals(before + 1, service.getGenres().size());
    }

    @Test
    public void getAuthors() {
        assertTrue(service.getAuthors().size() > 0);
    }

    @Test
    public void registerAuthor() {
        service.registerAuthor("New noweller");
        assertEquals(4, service.getAuthors().size());
    }

    @Test
    public void getBookByIsbn() {
        assertNotNull(service.getBookByIsbn(isbn));
    }

    @Test
    public void registerBook() {
        Book book = service.registerBook("003535-9988", "The new title of book.");
        assertNotEquals(0, book.getId());
    }
}