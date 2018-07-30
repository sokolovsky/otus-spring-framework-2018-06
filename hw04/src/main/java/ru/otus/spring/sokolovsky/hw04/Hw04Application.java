package ru.otus.spring.sokolovsky.hw04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EnableConfigurationProperties(ApplicationProperties.class)
public class Hw04Application {

	public static void main(String[] args) {
	    SpringApplication.run(Hw04Application.class, args);
	}
}
