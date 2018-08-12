package ru.otus.spring.sokolovsky.hw08.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.sokolovsky.hw08.domain.Book;

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

    @Test
    @DisplayName("Book is registered")
    void registerBook() {
        Book book = new Book("982164", "Title of book");

        book.addGenre(genreRepository.findAll().get(0));
        book.addGenre(genreRepository.findAll().get(1));

        book.addAuthor(authorRepository.findAll().get(0));
        book.addAuthor(authorRepository.findAll().get(1));
        book.addAuthor(authorRepository.findAll().get(2));

        bookRepository.save(book);

        Book storedBook = bookRepository.findByIsbn("982164");
        assertThat(storedBook.getGenres().size(), is(2));
        assertThat(storedBook.getAuthors().size(), is(3));
        assertEquals(storedBook.getTitle(), "Title of book");
    }

    @Test
    @DisplayName("A comment is added")
    void bookCommentAdding() {
        Book book = bookRepository.findByIsbn(isbn);
        assertNotNull(book);
        book.addComment("Some comment for the best book");

        assertEquals(1, book.getComments().size());
        bookRepository.save(book);

        Book reloadedBook = bookRepository.findByIsbn(isbn);
        assertNotSame(reloadedBook, book);
        assertEquals("Some comment for the best book", reloadedBook.getComments().iterator().next().getText());
    }
}
