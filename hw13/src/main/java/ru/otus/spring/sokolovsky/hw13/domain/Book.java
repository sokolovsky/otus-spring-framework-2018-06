package ru.otus.spring.sokolovsky.hw13.domain;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.Collectors;

@Data
@ToString
@Document(collection = "books")
public class Book {
    @Getter
    @Id
    private String id;

    @Getter
    @Indexed(unique = true)
    private String isbn;

    @Getter
    private String title;

    @Getter
    @DBRef
    private final Set<Genre> genres = new HashSet<>();

    @Getter
    @DBRef
    private final Set<Author> authors = new HashSet<>();

    @Getter
    private final List<Comment> comments = new ArrayList<>();

    public Book(String isbn, String title) {
        Objects.requireNonNull(isbn);
        Objects.requireNonNull(title);
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

    public List<String> authorNames() {
        return authors.stream().map(a -> a.getName()).collect(Collectors.toList());
    }

    public List<String> genreTitles() {
        return genres.stream().map(Genre::getTitle).collect(Collectors.toList());
    }
}
