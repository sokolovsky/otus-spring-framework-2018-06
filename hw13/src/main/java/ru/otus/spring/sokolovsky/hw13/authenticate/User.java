package ru.otus.spring.sokolovsky.hw13.authenticate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

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

    @Setter
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        if (Objects.isNull(password) || password.equals("")) {
            return "null"; // for right user detail recognition
        }
        return password;
    }
}
