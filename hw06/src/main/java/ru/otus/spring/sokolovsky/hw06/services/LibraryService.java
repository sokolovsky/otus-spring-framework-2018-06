package ru.otus.spring.sokolovsky.hw06.services;

import org.springframework.lang.Nullable;
import ru.otus.spring.sokolovsky.hw06.domain.Author;
import ru.otus.spring.sokolovsky.hw06.domain.Book;
import ru.otus.spring.sokolovsky.hw06.domain.Comment;
import ru.otus.spring.sokolovsky.hw06.domain.Genre;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface LibraryService {
    List<Book> getList(@Nullable String author, @Nullable String genre);

    List<Genre> getGenres();

    Genre registerGenre(@NotNull String name);

    List<Author> getAuthors();

    Author registerAuthor(@NotNull String name);

    Book getBookByIsbn(String isbn);

    Book registerBook(String isbn, String title);

    void save(Book newEntity);

    Author getAuthorById(long author);

    Genre getGenreById(long genre);
}
