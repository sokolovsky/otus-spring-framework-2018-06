package ru.otus.spring.sokolovsky.hw08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.sokolovsky.hw08.services.LibraryService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"/test-application.properties"})
@DirtiesContext
class Hw08ApplicationTests {

    @Autowired
    LibraryService service;

    @Test
    @DisplayName("Context is loaded")
    void contextLoading() {
        assertNotNull(service);
    }
}
