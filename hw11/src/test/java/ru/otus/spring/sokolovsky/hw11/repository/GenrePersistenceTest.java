package ru.otus.spring.sokolovsky.hw11.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.sokolovsky.hw11.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw11.domain.Genre;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("/test-application.properties")
class GenrePersistenceTest {

    @Autowired
    private GenreRepository repository;

    @BeforeEach
    void createFixture(@Autowired SeedCreator seed) {
        seed.create();
    }

    @Test
    @DisplayName("Genre repository exists")
    void getRepository() {
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Genre is saved")
    void saveEntity() {
        Genre genre = new Genre("Fantasy");
        repository.save(genre);
        assertThat(genre.getId(), not(0));
    }

    @Test
    @DisplayName("Genre is found")
    void findSavedEntity() {
        Genre genre = new Genre("Some genre");
        repository.save(genre).block();

        Genre storedEntity = repository.findByTitle("Some genre").block();

        assertNotSame(storedEntity, genre);
        assertThat(storedEntity.getTitle(), is("Some genre"));
    }

    @Test
    @DisplayName("Collection of genres is taken")
    void findCollection() {
        List<Genre> all = repository.findAll().collectList().block();
        assertTrue(all.size() >= 3);
        assertThat(all.get(0).getTitle(), not(""));
    }
}
