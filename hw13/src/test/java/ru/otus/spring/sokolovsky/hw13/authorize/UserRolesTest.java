package ru.otus.spring.sokolovsky.hw13.authorize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.sokolovsky.hw13.authenticate.TokenAwareAuthentication;
import ru.otus.spring.sokolovsky.hw13.authenticate.User;
import ru.otus.spring.sokolovsky.hw13.authenticate.UserDetailsService;
import ru.otus.spring.sokolovsky.hw13.authenticate.UserRepository;
import ru.otus.spring.sokolovsky.hw13.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw13.domain.Book;
import ru.otus.spring.sokolovsky.hw13.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"/test-application.properties"})
class UserRolesTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SeedCreator seedCreator;

    @Autowired
    private MutableAclService aclService;

    @Autowired
    private UserDetailsService userDetailsService;

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
        assertNotNull(userRepository);
    }

    @Test
    @DisplayName("Right users userRepository count")
    void rightUsersCount() {
        assertEquals(3, userRepository.findAll().size());
    }

    @Test
    @DisplayName("Right seed user roles")
    void rightUsersRoles() {
        User user = userRepository.findByLogin("user");
        assertEquals(0, user.getRoles().size());

        User editor = userRepository.findByLogin("editor");
        assertTrue(editor.getRoles().contains("ROLE_EDITOR"));

        User mainEditor = userRepository.findByLogin("main_editor");
        assertTrue(mainEditor.getRoles().contains("ROLE_MAIN_EDITOR"));
    }

    @Test
    @DisplayName("Giving access to book edit")
    void accessToBookEdit() {
        List<Book> books = bookRepository.findAll(PageRequest.of(1, 1)).stream().collect(Collectors.toList());
        if (books.size() == 0) {
            fail("Books are empty");
        }
        Book book = books.get(0);

        Authentication authentication = TokenAwareAuthentication.fromUserDetails(userDetailsService.loadUserByUsername("user"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // создать SID-ы для владельца и пользователя
        final Sid owner = new PrincipalSid(authentication);
        final Sid admin = new GrantedAuthoritySid("ROLE_ADMIN");
        // создать ObjectIdentity для бизнес сущности
        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(Book.class, book.getId());
        // создать пустой ACL
        final MutableAcl mutableAcl = aclService.createAcl(objectIdentity);

        // определить владельца сущности и права пользователей
        mutableAcl.setOwner(owner);
        mutableAcl.insertAce(mutableAcl.getEntries().size(), BasePermission.READ, owner, true);
        mutableAcl.insertAce(mutableAcl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true);
        // обновить ACL в БД
        aclService.updateAcl(mutableAcl);

        // проверка того что доступ на чтение существует, а на запись нет
        Acl acl = aclService.readAclById(objectIdentity);
        List<Permission> readPermissions = List.of(BasePermission.READ);
        List<Permission> writePermissions = List.of(BasePermission.WRITE);

        assertTrue(acl.isGranted(readPermissions, List.of(owner), false));
        assertTrue(!acl.isGranted(writePermissions, List.of(owner), false));
    }
}
