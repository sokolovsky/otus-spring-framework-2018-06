package ru.otus.spring.sokolovsky.hw07.domain;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column
    @Getter
    private final Date time = Calendar.getInstance().getTime();

    @Column
    @Getter
    private final String text;

    public Comment(String text) {
        this.text = text;
    }
}
