package ru.otus.spring.sokolovsky.hw12.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.otus.spring.sokolovsky.hw12.domain.Book;
import ru.otus.spring.sokolovsky.hw12.services.BookCommunityService;
import ru.otus.spring.sokolovsky.hw12.services.LibraryService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"/test-application.properties"})
@WebAppConfiguration
class CommunityControllerTest extends ControllerTest {

    @Test
    @DisplayName("Comment into book has been added")
    void addCommentToBook() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);
        BookCommunityService communityService = mock(BookCommunityService.class);
        CommunityController controller = new CommunityController(
                libraryService, communityService
        );

        MockMvc rest = getRestService(controller);

        Book mockBook = new Book("", "");
        when(libraryService.getBookById("20")).thenReturn(mockBook);

        MockHttpServletRequestBuilder post = post("/comment/book/add/20")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"text\": \"Some message from the best book\"}");

        injectToken(post);

        rest.perform(post)
            .andExpect(status().isOk())
            .andDo(h -> {
                System.out.println("Response `" + h.getResponse().getContentAsString()+"`");
            })
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

        MockMvc rest = getRestService(controller);
        rest.perform(get("/comment/book/get/20"))
            .andExpect(status().isOk())
                .andExpect(result -> System.out.println(result.getResponse().getStatus()))
            .andExpect(jsonPath("$[0].text", is("Some comment for testing")));
    }
}
