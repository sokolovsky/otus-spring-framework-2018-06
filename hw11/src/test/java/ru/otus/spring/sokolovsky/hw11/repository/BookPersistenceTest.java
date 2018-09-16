package ru.otus.spring.sokolovsky.hw11.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.sokolovsky.hw11.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw11.domain.Author;
import ru.otus.spring.sokolovsky.hw11.domain.Book;
import ru.otus.spring.sokolovsky.hw11.domain.Genre;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("/test-application.properties")
class BookPersistenceTest {

    private final String isbn = "978-5-9905833-8-2";

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

    @BeforeEach
    void createFixture(@Autowired SeedCreator seed) {
        seed.create();
    }

    @Test
    @DisplayName("Book is registered")
    void registerBook() {
        Book book = new Book("982164", "Title of book");

        book.addGenre(genreRepository.findAll().blockFirst());
        book.addGenre(genreRepository.findAll().collectList().block().get(1));

        book.addAuthor(authorRepository.findAll().blockFirst());
        book.addAuthor(authorRepository.findAll().collectList().block().get(1));
        book.addAuthor(authorRepository.findAll().collectList().block().get(2));

        bookRepository.save(book).block();

        Book storedBook = bookRepository.findByIsbn("982164").block();
        assertThat(storedBook.getGenres().size(), is(2));
        assertThat(storedBook.getAuthors().size(), is(3));
        assertEquals(storedBook.getTitle(), "Title of book");
    }

    @Test
    @DisplayName("A comment is added")
    void bookCommentAdding() {
        Book book = bookRepository.findByIsbn(isbn).block();
        assertNotNull(book);
        book.addComment("Some comment for the best book");

        assertEquals(1, book.getComments().size());
        bookRepository.save(book).block();

        Book reloadedBook = bookRepository.findByIsbn(isbn).block();
        assertNotSame(reloadedBook, book);
        String actualCommentText = reloadedBook.getComments().iterator().next().getText();
        assertEquals("Some comment for the best book", actualCommentText);
    }

    @Test
    @DisplayName("Books are found by author")
    void findBookByAuthor() {
        Author author = authorRepository.findByName("Александр Пушкин").block();
        List<Book> books = bookRepository.findByAuthors(author).collectList().block();
        assertThat(books.size(), greaterThan(2));
    }

    @Test
    @DisplayName("Books are found by genre")
    void findBookByGenre() {
        Genre genre = genreRepository.findByTitle("Русская классика").block();
        List<Book> books = bookRepository.findByGenres(genre).collectList().block();
        assertThat(books.size(), greaterThan(1));
    }

    @Test
    @DisplayName("Books are found by genre with author")
    void findBookByAuthorAndGenre() {
        Author author = authorRepository.findByName("Александр Пушкин").block();
        Genre genre = genreRepository.findByTitle("Классическая проза").block();
        List<Book> books = bookRepository.findByAuthorsAndGenres(author, genre).collectList().block();
        assertThat(books.size(), greaterThan(0));
    }
}
