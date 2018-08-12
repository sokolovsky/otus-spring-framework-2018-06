package ru.otus.spring.sokolovsky.hw08.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.sokolovsky.hw08.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw08.domain.Author;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataMongoTest
@TestPropertySource(locations = {"/test-application.properties"})
class AuthorPersistenceTest {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private SeedCreator seedCreator;

    @BeforeEach
    void createFixtures(@Autowired SeedCreator seed) {
        seed.create();
    }

    @Test
    @DisplayName("Data initializer is found")
    void getMongobeeService() {
        assertNotNull(seedCreator);
    }

    @Test
    @DisplayName("Repository is autowired")
    void getRepository() {
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Entity is saved")
    void saveEntity() {
        Author author = new Author("Author");
        repository.save(author);
        assertThat(author.getId(), not(0));
    }

    @Test
    @DisplayName("Saved entity is found")
    void findSavedEntity() {
        Author author = new Author("Some author");
        repository.save(author);

        Author storedEntity = repository.findByName("Some author");

        assertThat(storedEntity.getName(), is("Some author"));
    }

    @Test
    @DisplayName("Collection of entities is taken")
    void findCollection() {
        List<Author> all = repository.findAll();
        assertTrue(all.size() >= 3);
        assertThat(all.get(0).getName(), not(""));
    }

    @Test
    @DisplayName("Stored id is mapped onto entities in right way")
    void storedIdIsNotNull() {
        Author author = repository.findAll().get(0);
        assertNotNull(author.getId());
    }
}

