package ru.otus.spring.sokolovsky.presentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStreamReader;
import java.io.PrintStream;

@Configuration
public class PresentationConfigure {
    @Bean
    public InputStreamReader inputStreamReader() {
        return new InputStreamReader(System.in);
    }

    @Bean
    public PrintStream printStream() {
        return System.out;
    }
}
