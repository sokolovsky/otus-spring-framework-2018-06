package ru.otus.spring.sokolovsky.hw12.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.sokolovsky.hw12.domain.Book;
import ru.otus.spring.sokolovsky.hw12.services.BookCommunityService;
import ru.otus.spring.sokolovsky.hw12.services.LibraryService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataMongoTest
@TestPropertySource(locations = {"/test-application.properties"})
public class CommunityControllerTest {

    @Test
    @DisplayName("Comment into book has been added")
    void addCommentToBook() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);
        BookCommunityService communityService = mock(BookCommunityService.class);
        CommunityController controller = new CommunityController(
                libraryService, communityService
        );

        MockMvc rest = standaloneSetup(controller).build();

        Book mockBook = new Book("", "");
        when(libraryService.getBookById("20")).thenReturn(mockBook);

        rest.perform(post("/comment/book/add/20")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"text\": \"Some message from the best book\"}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)));

        verify(communityService).registerBookComment(same(mockBook), eq("Some message from the best book"));
    }

    @Test
    @DisplayName("Getting comments about book")
    void getComments() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);
        BookCommunityService communityService = mock(BookCommunityService.class);
        CommunityController controller = new CommunityController(
                libraryService, communityService
        );
        Book mockBook = new Book("", "");
        mockBook.addComment("Some comment for testing");
        when(libraryService.getBookById("20")).thenReturn(mockBook);

        MockMvc rest = standaloneSetup(controller).build();
        rest.perform(get("/comment/book/get/20"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].text", is("Some comment for testing")));
    }
}
