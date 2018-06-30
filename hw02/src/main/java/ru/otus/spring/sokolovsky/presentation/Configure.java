package ru.otus.spring.sokolovsky.presentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStreamReader;
import java.io.PrintStream;

@Configuration
public class Configure {
    @Bean
    public InputStreamReader inputStream() {
        return new InputStreamReader(System.in);
    }

    @Bean
    public PrintStream outputStream() {
        return System.out;
    }
}
