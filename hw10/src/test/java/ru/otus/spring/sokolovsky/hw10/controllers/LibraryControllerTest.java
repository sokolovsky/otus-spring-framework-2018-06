package ru.otus.spring.sokolovsky.hw10.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.sokolovsky.hw10.domain.Author;
import ru.otus.spring.sokolovsky.hw10.domain.Book;
import ru.otus.spring.sokolovsky.hw10.domain.Genre;
import ru.otus.spring.sokolovsky.hw10.services.LibraryService;
import ru.otus.spring.sokolovsky.hw10.services.NotExistException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataMongoTest
@TestPropertySource(locations = {"/test-application.properties"})
public class LibraryControllerTest {

    @Test
    @DisplayName("Book list call without any params")
    void bookListTest() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);
        LibraryController controller = new LibraryController(
                libraryService
        );

        when(libraryService.getList(null, null))
                .thenReturn(new ArrayList<>() {
                    {
                        Field id = Book.class.getDeclaredField("id");
                        id.setAccessible(true);

                        Book book = new Book("1-isbn", "Some title of tested book");
                        book.addAuthor(new Author("Ivan"));
                        book.addAuthor(new Author("Petr"));
                        book.addGenre(new Genre("horror"));
                        id.set(book, "111");
                        add(book);

                        book = new Book("2-isbn", "Another title of another book");
                        book.addAuthor(new Author("Ivan"));
                        book.addGenre(new Genre("comedy"));
                        id.set(book, "222");

                        add(book);
                    }
                });

        MockMvc rest = standaloneSetup(controller).build();

        rest.perform(get("/book/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is("111")))
                .andExpect(jsonPath("$[1].id", is("222")))
                .andExpect(jsonPath("$[0].authors[1].name", isIn(new String[]{"Petr", "Ivan"})))
                .andExpect(jsonPath("$[0].authors[0].name", isIn(new String[]{"Petr", "Ivan"})));
    }

    @Test
    @DisplayName("Book list uses library service properly")
    void bookListByGenreAndAuthorTest() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);
        LibraryController controller = new LibraryController(
                libraryService
        );


        MockMvc rest = standaloneSetup(controller).build();
        rest.perform(get("/book/list?genre=111&author=222"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(libraryService, times(1)).getList("222", "111");
    }

    @Test
    @DisplayName("Getting concrete book")
    void bookInfo() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);
        LibraryController controller = new LibraryController(
                libraryService
        );

        Book book = new Book("1-isbn", "title");
        when(libraryService.getBookById("1"))
                .thenReturn(book);

        MockMvc rest = standaloneSetup(controller).build();

        rest.perform(get("/book/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.isbn", is("1-isbn")));
    }

    @Test
    @DisplayName("Trying to get wrong book")
    void missDetailBook() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);
        LibraryController controller = new LibraryController(
                libraryService
        );

        when(libraryService.getBookById("1"))
                .thenThrow(NotExistException.class);

        MockMvc rest = standaloneSetup(controller).build();

        rest.perform(get("/book/get/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Register a new book")
    void registerNewBook() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);
        LibraryController controller = new LibraryController(
                libraryService
        );

        when(libraryService.registerBook("10-isbn", "title"))
                .thenReturn(new Book("10-isbn", "title"));

        MockMvc rest = standaloneSetup(controller).build();

        rest.perform(post("/book/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"title\": \"title\", \"isbn\": \"10-isbn\", \"authors\": [\"1\", \"2\"], \"genres\": [\"10\", \"20\"]}"))
                .andExpect(status().isOk());

        verify(libraryService, times(1)).fillAuthors(any(), eq(List.of("1", "2")));
        verify(libraryService, times(1)).fillGenres(any(), eq(List.of("10", "20")));
        verify(libraryService, times(1)).save(argThat(b -> {
            if (!b.getTitle().equals("title")) {
                return false;
            }
            if (!b.getIsbn().equals("10-isbn")) {
                return false;
            }
            return true;
        }));
    }

    @Test
    @DisplayName("Test of update action")
    void updateAssertion() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);
        LibraryController controller = new LibraryController(
                libraryService
        );

        MockMvc rest = standaloneSetup(controller).build();

        when(libraryService.getBookById("10"))
                .thenReturn(new Book("", ""));

        rest.perform(
                post("/book/update/10")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{}")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    @DisplayName("For deletion called right service method")
    void deletion() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);
        LibraryController controller = new LibraryController(
                libraryService
        );

        MockMvc rest = standaloneSetup(controller).build();

        Book mockBook = new Book("", "");
        when(libraryService.getBookById("10"))
                .thenReturn(mockBook);

        rest.perform(post("/book/delete/10")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(libraryService, times(1)).delete(same(mockBook));
    }
}
