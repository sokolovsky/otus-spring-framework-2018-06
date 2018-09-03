package ru.otus.spring.sokolovsky.hw10.domain;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Calendar;
import java.util.Date;

@Data
@ToString
public class Comment {

    @Getter
    @Id
    private String id;

    @Getter
    private final Date time = Calendar.getInstance().getTime();

    @Getter
    private final String text;

    Comment(String text) {
        this.text = text;
    }
}
