package ru.otus.spring.sokolovsky.hw06.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
public class BookDaoTest {

    @Autowired
    BookDao dao;

    @Autowired
    AuthorDao authorDao;

    @Autowired
    GenreDao genreDao;

    @Test
    public void registerBook() {
        Book book = new Book("982164", "Title of book");

        book.addGenre(genreDao.getAll().get(0));
        book.addGenre(genreDao.getAll().get(1));

        book.addAuthor(authorDao.getAll().get(0));
        book.addAuthor(authorDao.getAll().get(1));
        book.addAuthor(authorDao.getAll().get(2));

        dao.save(book);

        Book storedBook = dao.getByISBN("982164");
        assertThat(storedBook.getGenres().size(), is(2));
        assertThat(storedBook.getAuthors().size(), is(3));
        assertEquals(storedBook.getTitle(), "Title of book");
    }
}
