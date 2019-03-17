package ru.otus.spring.sokolovsky.hw13.authorize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.sokolovsky.hw13.authenticate.User;
import ru.otus.spring.sokolovsky.hw13.authenticate.UserRepository;
import ru.otus.spring.sokolovsky.hw13.changelogs.SeedCreator;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"/test-application.properties"})
class UserRolesTest {

    @Autowired
    private UserRepository repository;

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
    @DisplayName("Right users repository count")
    void rightUsersCount() {
        assertEquals(3, repository.findAll().size());
    }

    @Test
    @DisplayName("Right seed user roles")
    void rightUsersRoles() {
        User user = repository.findByLogin("user");
        assertEquals(0, user.getRoles().size());

        User editor = repository.findByLogin("editor");
        assertTrue(editor.getRoles().contains("ROLE_EDITOR"));

        User mainEditor = repository.findByLogin("main_editor");
        assertTrue(mainEditor.getRoles().contains("ROLE_MAIN_EDITOR"));
    }
}
