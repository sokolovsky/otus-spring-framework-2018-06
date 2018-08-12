package ru.otus.spring.sokolovsky.hw08.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.HashSet;

@Data
@ToString(exclude = {"books"})
@Document(collection = "genres")
public class Genre extends BaseEntity {
    @Getter
    @Setter
    private String title;

    public Genre(String title) {
        this.title = title;
    }
}
