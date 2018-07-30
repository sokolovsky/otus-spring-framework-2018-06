package ru.otus.spring.sokolovsky.hw05.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.HashSet;

@Data
@ToString
public class Book {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String ISBN;

    @Getter
    @Setter
    private String title;

    @Getter
    private final Collection<Genre> genres = new HashSet<>();

    @Getter
    private final Collection<Author> authors = new HashSet<>();

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }
}
