package ru.otus.spring.sokolovsky.hw05.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
public class GenreDaoTest {

    @Autowired
    private GenreDao dao;

    @Test
    public void saveEntity() {
        Genre genre = new Genre();
        genre.setTitle("Fantasy");
        dao.insert(genre);
        assertThat(genre.getId(), not(0));
    }

    @Test
    public void findSavedEntity() {
        Genre genre = new Genre();
        genre.setTitle("Some genre");
        dao.insert(genre);

        Genre storedEntity = dao.findByTitle("Some genre");

        assertNotSame(storedEntity, genre);
        assertThat(storedEntity.getTitle(), is("Some genre"));
    }

    @Test
    public void findCollection() {
        List<Genre> all = dao.getAll();
        assertTrue(all.size() >= 3);
        assertThat(all.get(0).getTitle(), not(""));
    }
}
