package ru.otus.spring.sokolovsky.hw06.domain;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Data
@ToString
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Getter
    @Column
    private final String ISBN;

    @Getter
    @Column
    private final String title;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "books_genres",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "genreId")
    )
    private final Collection<Genre> genres = new HashSet<>();

    @Getter
    @ManyToMany
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "authorId")
    )
    private final Collection<Author> authors = new HashSet<>();

    @Getter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final Collection<Comment> comments = new ArrayList<>();

    public Book(String ISBN, String title) {
        this.ISBN = ISBN;
        this.title = title;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void addComment(String text) {
        Comment comment = new Comment(this, text);
        comments.add(comment);
    }
}
