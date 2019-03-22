package ru.otus.spring.sokolovsky.hw13.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
    @Setter
    private Date time = Calendar.getInstance().getTime();

    @Getter
    @Setter
    private String text;

    Comment(String text) {
        this.text = text;
    }
}
