package ru.otus.spring.sokolovsky.hw06.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Data
@ToString
@Entity
@Table(name = "genres")
public class Genre extends BaseEntity {
    @Getter
    @Setter
    @Column
    private String title;

    @Getter
    @ManyToMany
    protected final Collection<Book> books = new HashSet<>();
}
