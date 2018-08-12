package ru.otus.spring.sokolovsky.hw08.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;

class BaseEntity {
    @Getter
    @Id
    private String id;
}
