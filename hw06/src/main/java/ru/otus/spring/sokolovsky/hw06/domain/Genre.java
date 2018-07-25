package ru.otus.spring.sokolovsky.hw06.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
public class Genre {
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String title;
}
