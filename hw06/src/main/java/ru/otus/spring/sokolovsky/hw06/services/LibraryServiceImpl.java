package ru.otus.spring.sokolovsky.hw06.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.sokolovsky.hw06.domain.*;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
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
    @Transactional(readOnly = true)
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
        return genreDao.findAll();
    }

    @Override
    public Genre registerGenre(@NotNull String title) {
        Genre genre = new Genre(title);
        genreDao.save(genre);
        return genre;
    }

    @Override
    public List<Author> getAuthors() {
        return authorDao.findAll();
    }

    @Override
    public Author registerAuthor(@NotNull String name) {
        Author author = new Author(name);
        authorDao.save(author);
        return author;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return bookDao.findByIsbn(isbn);
    }

    @Override
    public Book registerBook(String isbn, String title) {
        Book book = new Book(isbn, title);
        bookDao.save(book);
        return book;
    }

    @Override
    public void save(Book book) {
        bookDao.save(book);
    }

    @Override
    public Author getAuthorById(long id) {
        return authorDao.findById(id);
    }

    @Override
    public Genre getGenreById(long id) {
        return genreDao.findById(id);
    }
}
