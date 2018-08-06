package ru.otus.spring.sokolovsky.hw07;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.otus.spring.sokolovsky.hw07.repository.DomainRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = DomainRepositoryImpl.class)
public class Hw07Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw07Application.class, args);
    }
}
