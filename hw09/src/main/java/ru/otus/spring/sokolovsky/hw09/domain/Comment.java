package ru.otus.spring.sokolovsky.hw09.domain;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Calendar;
import java.util.Date;

@Data
@ToString
public class Comment extends BaseEntity {

    @Getter
    private final Date time = Calendar.getInstance().getTime();

    @Getter
    private final String text;

    Comment(String text) {
        this.text = text;
    }
}
