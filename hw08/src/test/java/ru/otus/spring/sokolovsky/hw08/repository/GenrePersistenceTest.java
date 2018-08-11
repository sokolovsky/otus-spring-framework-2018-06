package ru.otus.spring.sokolovsky.hw08.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.sokolovsky.hw08.domain.Genre;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GenrePersistenceTest {

    @Autowired
    private GenreRepository repository;

    @Test
    public void saveEntity() {
        Genre genre = new Genre("Fantasy");
        repository.save(genre);
        assertThat(genre.getId(), not(0));
    }

    @Test
    public void findSavedEntity() {
        Genre genre = new Genre("Some genre");
        repository.save(genre);

        Genre storedEntity = repository.findByTitle("Some genre");

        assertNotSame(storedEntity, genre);
        assertThat(storedEntity.getTitle(), is("Some genre"));
    }

    @Test
    public void findCollection() {
        List<Genre> all = repository.findAll();
        assertTrue(all.size() >= 3);
        assertThat(all.get(0).getTitle(), not(""));
    }
}
