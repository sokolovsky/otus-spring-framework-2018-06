package ru.otus.spring.sokolovsky.hw08.domain;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;

@Data
@ToString
@Document(collection = "comments")
public class Comment extends BaseEntity {

    @Getter
    private final Date time = Calendar.getInstance().getTime();

    @Getter
    private final String text;

    public Comment(String text) {
        this.text = text;
    }
}
