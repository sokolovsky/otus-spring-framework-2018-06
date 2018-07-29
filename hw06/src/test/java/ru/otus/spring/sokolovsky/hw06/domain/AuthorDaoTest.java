package ru.otus.spring.sokolovsky.hw06.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.sokolovsky.hw06.repository.AuthorRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@Transactional
public class AuthorDaoTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    public void saveEntity() {
        Author author = new Author();
        author.setName("Author");
        repository.save(author);
        assertThat(author.getId(), not(0));
    }

    @Test
    public void findSavedEntity() {
        Author author = new Author();
        author.setName("Some author");
        repository.save(author);

        Author storedEntity = repository.findByName("Some author");

        assertNotSame(storedEntity, author);
        assertThat(storedEntity.getName(), is("Some author"));
    }

    @Test
    public void findCollection() {
        List<Author> all = repository.findAll();
        assertTrue(all.size() >= 3);
        assertThat(all.get(0).getName(), not(""));
    }
}
