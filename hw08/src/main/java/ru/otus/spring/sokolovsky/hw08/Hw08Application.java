package ru.otus.spring.sokolovsky.hw08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Hw08Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw08Application.class, args);
    }
}
