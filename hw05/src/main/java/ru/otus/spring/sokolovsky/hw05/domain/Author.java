package ru.otus.spring.sokolovsky.hw05.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
public class Author {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;
}
