package ru.otus.spring.sokolovsky.hw08.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.HashSet;

@Data
@ToString(exclude = {"books"})
@Document(collection = "authors")
public class Author extends BaseEntity {
    @Getter
    @Setter
    @Indexed
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
