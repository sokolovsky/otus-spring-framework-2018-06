package ru.otus.spring.sokolovsky.hw11.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "genres")
public class Genre {
    @Getter
    @Id
    private String id;

    @Getter
    @Setter
    private String title;

    public Genre(String title) {
        this.title = title;
    }
}
