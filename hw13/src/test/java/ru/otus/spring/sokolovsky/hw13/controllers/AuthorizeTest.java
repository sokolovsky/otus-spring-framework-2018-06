package ru.otus.spring.sokolovsky.hw13.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring.sokolovsky.hw13.authenticate.TokenAwareAuthentication;
import ru.otus.spring.sokolovsky.hw13.authenticate.UserDetailsService;
import ru.otus.spring.sokolovsky.hw13.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw13.domain.Book;
import ru.otus.spring.sokolovsky.hw13.services.LibraryService;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = {"/test-application.properties"})
class AuthorizeTest extends ControllerTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private LibraryController libraryController;

    @Autowired
    private MutableAclService aclService;

    @Autowired
    SeedCreator seed;

    @BeforeEach
    void createFixtures() {
        seed.create();
    }

    @Test
    @DisplayName("Public action authorization")
    void usePublicAction()  {
        assertDoesNotThrow(() -> libraryController.books("nothing", "nothing"));
    }

    @Test
    @DisplayName("Ban with adding book")
    @WithMockUser
    void banOnAddingBooks() {
        assertThrows(AccessDeniedException.class, () -> libraryController.bookRegister(new HashMap<>()));
    }

    @Test
    @WithMockUser(username = "user", roles = {"EDITOR"})
    @DisplayName("Allow adding book by editor")
    void allowOnAddingBooks() {
        try {
            libraryController.bookRegister(new HashMap<>());
        } catch (AccessDeniedException e) {
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    @DisplayName("Disallow adding book by editor")
    @WithMockUser(username = "user", roles = {"EDITOR"})
    void disallowDeletingBooks() {
        assertThrows(AccessDeniedException.class, () -> libraryController.bookDelete("some id of book"));
    }

    @Test
    @DisplayName("Allowing to edit book by main editor")
    @WithMockUser(roles = {"MAIN_EDITOR"})
    void allowingToEditBookByMainEditor() {
        try {
            libraryController.bookUpdate("some id", new HashMap<>());
        } catch (AccessDeniedException e) {
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    @WithMockUser(roles = {"EDITOR"})
    @DisplayName("Allowing to edit book by main editor")
    void disallowingToEditBootByEditor() {
        assertThrows(AccessDeniedException.class, () -> libraryController.bookUpdate("some id", new HashMap<>()));
    }

    @Test
    @DisplayName("Allowing to edit owned books")
    void allowingToEditOwnedBooks() {
        Authentication authentication = TokenAwareAuthentication.fromUserDetails(userDetailsService.loadUserByUsername("editor"));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        final Book book = libraryService.getAnyBook();
        final Sid owner = new PrincipalSid(authentication);

        MutableAcl acl = aclService.createAcl(new ObjectIdentityImpl(Book.class, book.getId()));
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, owner, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, owner, true);
        aclService.updateAcl(acl);

        try {
            libraryController.bookUpdate(book.getId(), new HashMap<>());
        } catch (AccessDeniedException e) {
            fail();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    @DisplayName("Disallowing to edit books of another editor")
    void disallowToEditBooks() {
        Authentication authentication = TokenAwareAuthentication.fromUserDetails(userDetailsService.loadUserByUsername("editor"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final Book book = libraryService.getAnyBook();
        assertThrows(AccessDeniedException.class, () -> libraryController.bookUpdate(book.getId(), new HashMap<>()));
    }
}
