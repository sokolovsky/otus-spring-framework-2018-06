package ru.otus.spring.sokolovsky.hw06.domain;

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
public class Comment extends BaseEntity{

    @Column
    @Getter
    private final Date dateTime = Calendar.getInstance().getTime();

    @Column
    @Getter
    private final String text;

    @ManyToOne
    @Getter
    private final Book book;

    public Comment(Book book, String text) {
        this.text = text;
        this.book = book;
    }
}
