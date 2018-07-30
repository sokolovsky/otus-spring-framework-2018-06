package ru.otus.spring.sokolovsky.hw06.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Data
@ToString(exclude = {"books"})
@Entity
@Table(name = "authors")
public class Author extends BaseEntity {
    @Getter
    @Setter
    @Column
    private String name;

    @Getter
    @ManyToMany
    protected final Collection<Book> books = new HashSet<>();

    public Author(String name) {
        this.name = name;
    }
}
