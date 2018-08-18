package ru.otus.spring.sokolovsky.hw09.domain;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString
@Document(collection = "books")
public class Book extends BaseEntity {

    @Getter
    @Indexed
    private final String isbn;

    @Getter
    private final String title;

    @Getter
    @DBRef
    private final Set<Genre> genres = new HashSet<>();

    @Getter
    @DBRef
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
