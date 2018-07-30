package ru.otus.spring.sokolovsky.hw06.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw06.domain.*;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public LibraryServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public List<Book> getList(String author, String genre) {
        List<Book> emptyResult = Collections.emptyList();

        Author authorEntity = null;
        if (Objects.nonNull(author)) {
            authorEntity = authorDao.findByName(author);
            if (Objects.isNull(authorEntity)) {
                return emptyResult;
            }
        }

        Genre genreEntity = null;
        if (Objects.nonNull(genre)) {
            genreEntity = genreDao.findByTitle(genre);
            if (Objects.isNull(genreEntity)) {
                return emptyResult;
            }
        }

        if (Objects.isNull(genreEntity) && Objects.isNull(authorEntity)) {
            return bookDao.findAll();
        }
        if (!Objects.isNull(genreEntity) && Objects.isNull(authorEntity)) {
            return bookDao.findByGenre(genreEntity);
        }
        if (Objects.isNull(genreEntity) && !Objects.isNull(authorEntity)) {
            return bookDao.findByAuthor(authorEntity);
        }
        if (!Objects.isNull(genreEntity) && !Objects.isNull(authorEntity)) {
            return bookDao.findByAuthorAndGenre(authorEntity, genreEntity);
        }
        return emptyResult;
    }

    @Override
    public List<Genre> getGenres() {
        return null;
    }

    @Override
    public Genre registerGenre(@NotNull String name) {
        return null;
    }

    @Override
    public List<Author> getAuthors() {
        return null;
    }

    @Override
    public Author registerAuthor(@NotNull String name) {
        return null;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return bookDao.findByIsbn(isbn);
    }

    @Override
    public Book registerBook(String isbn, String title) {
        return null;
    }

    @Override
    public void save(Book book) {
        bookDao.save(book);
    }

    @Override
    public Author getAuthorById(long author) {
        return null;
    }

    @Override
    public Genre getGenreById(long genre) {
        return null;
    }
}
