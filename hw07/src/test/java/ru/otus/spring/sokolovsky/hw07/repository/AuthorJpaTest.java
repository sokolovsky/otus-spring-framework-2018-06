package ru.otus.spring.sokolovsky.hw07.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.sokolovsky.hw07.domain.Author;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@DataJpaTest
@TestPropertySource("classpath:test-application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorJpaTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    public void saveEntity() {
        Author author = new Author("Author");
        repository.save(author);
        assertThat(author.getId(), not(0));
    }

    @Test
    public void findSavedEntity() {
        Author author = new Author("Some author");
        repository.save(author);

        Author storedEntity = repository.findByName("Some author");

        assertThat(storedEntity.getName(), is("Some author"));
    }

    @Test
    public void findCollection() {
        List<Author> all = repository.findAll();
        assertTrue(all.size() >= 3);
        assertThat(all.get(0).getName(), not(""));
    }
}

