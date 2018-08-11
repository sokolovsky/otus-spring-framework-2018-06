package ru.otus.spring.sokolovsky.hw08.domain;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Data
@ToString
public class Book extends BaseEntity {

    @Getter
    private final String isbn;

    @Getter
    private final String title;

    @Getter
    private final Set<Genre> genres = new HashSet<>();

    @Getter
    private final Set<Author> authors = new HashSet<>();

    @Getter
    private final List<Comment> comments = new ArrayList<>();

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public Comment addComment(String text) {
        Comment comment = new Comment(text);
        comments.add(comment);
        return comment;
    }
}
