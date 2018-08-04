package ru.otus.spring.sokolovsky.hw07;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
public class Hw07ApplicationTests {

    @Test
    public void contextLoads() {
    }
}
