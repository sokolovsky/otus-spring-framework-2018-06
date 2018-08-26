package ru.otus.spring.sokolovsky.hw10.services;

import org.springframework.lang.Nullable;
import ru.otus.spring.sokolovsky.hw10.domain.Author;
import ru.otus.spring.sokolovsky.hw10.domain.Book;
import ru.otus.spring.sokolovsky.hw10.domain.Genre;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface LibraryService {
    List<Book> getList(@Nullable String authorId, @Nullable String genreId);

    List<Book> getList();

    List<Genre> getGenres();

    Genre registerGenre(@NotNull String name);

    List<Author> getAuthors();

    Author registerAuthor(@NotNull String name);

    Book getBookByIsbn(String isbn);

    Book registerBook(String isbn, String title);

    void save(Book newEntity);

    /**
     * Return null if not present
     * @param author
     * @return
     */
    Author getAuthorById(String author);

    /**
     * Returns null if not present
     * @param genre
     * @return
     */
    Genre getGenreById(String genre);

    void delete(Book book);
}
