package ru.otus.spring.sokolovsky.hw09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.sokolovsky.hw09.services.LibraryService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"/test-application.properties"})
class Hw09ApplicationTests {

    @Autowired
    LibraryService service;

    @Test
    @DisplayName("Context is loaded")
    void contextLoading() {
        assertNotNull(service);
    }
}

