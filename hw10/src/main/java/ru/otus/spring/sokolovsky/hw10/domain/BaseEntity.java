package ru.otus.spring.sokolovsky.hw10.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;

public class BaseEntity {
    @Getter
    @Id
    private String id;
}
