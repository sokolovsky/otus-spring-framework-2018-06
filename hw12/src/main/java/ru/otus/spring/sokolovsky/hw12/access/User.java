package ru.otus.spring.sokolovsky.hw12.access;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Getter
    @Id
    private String id;

    @Getter
    @Setter
    @Indexed
    private String login;

    @Getter
    @Setter
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
