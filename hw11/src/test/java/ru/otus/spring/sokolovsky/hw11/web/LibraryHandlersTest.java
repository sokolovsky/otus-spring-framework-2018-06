package ru.otus.spring.sokolovsky.hw11.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.configuration.RouterConfiguration;
import ru.otus.spring.sokolovsky.hw11.domain.Author;
import ru.otus.spring.sokolovsky.hw11.domain.Book;
import ru.otus.spring.sokolovsky.hw11.domain.Genre;
import ru.otus.spring.sokolovsky.hw11.services.LibraryService;
import ru.otus.spring.sokolovsky.hw11.services.NotExistException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"/test-application.properties"})
class LibraryHandlersTest {

    AnnotationConfigApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean(LibraryHandlers.class);
        context.register(RouterConfiguration.class);
        return context;
    }

    @Test
    @DisplayName("Book list call without any params")
    void bookListTest() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);

        when(libraryService.getList(null, null))
                .thenReturn(Flux.fromIterable(new ArrayList<>() {
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
                }));

        AnnotationConfigApplicationContext context = createApplicationContext();
        context.registerBean(LibraryService.class, () -> libraryService);
        context.refresh();

        createWebClient(context).get()
            .uri("/book/list")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody()
            .jsonPath("$[0].id").isEqualTo("111")
            .jsonPath("$[1].id").isEqualTo("222")
            .jsonPath("$[0].authors[0].name").exists()
            .jsonPath("$[2].authors[0].name").doesNotExist()
            .jsonPath("$[0].authors[1].name").exists();
    }

    private WebTestClient createWebClient(ApplicationContext context) {
        return WebTestClient
                    .bindToWebHandler(
                        RouterFunctions.toWebHandler((RouterFunction<?>) context.getBean("monoRouterFunction"))
                    ).build();
    }

    @Test
    @DisplayName("Book list uses library service properly")
    void bookListByGenreAndAuthorTest() {
        LibraryService libraryService = mock(LibraryService.class);
        when(libraryService.getList(any(), any())).thenReturn(Flux.empty());

        AnnotationConfigApplicationContext context = createApplicationContext();
        context.registerBean(LibraryService.class, () -> libraryService);
        context.refresh();

        createWebClient(context).get()
            .uri("/book/list?genre=111&author=222")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody().json("[]");

        verify(libraryService, times(1)).getList("222", "111");
    }

    @Test
    @DisplayName("Getting concrete book")
    void bookInfo() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);

        Book book = new Book("1-isbn", "title");
        when(libraryService.getBookById("1"))
                .thenReturn(Mono.just(book));

        AnnotationConfigApplicationContext context = createApplicationContext();
        context.registerBean(LibraryService.class, () -> libraryService);
        context.refresh();

        createWebClient(context).get()
            .uri("/book/get/1")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody().jsonPath("$.isbn").isEqualTo("1-isbn");
    }

    @Test
    @DisplayName("Trying to get wrong book")
    void missDetailBook() {
        LibraryService libraryService = mock(LibraryService.class);

        when(libraryService.getBookById("1"))
                .thenThrow(NotExistException.class);

        AnnotationConfigApplicationContext context = createApplicationContext();
        context.registerBean(LibraryService.class, () -> libraryService);
        context.refresh();

        createWebClient(context).get()
            .uri("/book/get/1")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().is4xxClientError();
    }

    @Test
    @DisplayName("Register a new book")
    void registerNewBook() {
        LibraryService libraryService = mock(LibraryService.class);

        when(libraryService.fillAuthors(any(), any()))
            .then(invocation -> {
                Book book = invocation.getArgument(0);
                List<String> ids = invocation.getArgument(1);
                ids.forEach(id -> book.addAuthor(new Author(id)));
                return Mono.empty();
            });

        when(libraryService.fillGenres(any(), any()))
            .then(invocation -> {
                Book book = invocation.getArgument(0);
                List<String> ids = invocation.getArgument(1);
                ids.forEach(id -> book.addGenre(new Genre(id)));
                return Mono.empty();
            });

        when(libraryService.save(any()))
            .thenReturn(Mono.empty());

        AnnotationConfigApplicationContext context = createApplicationContext();
        context.registerBean(LibraryService.class, () -> libraryService);
        context.refresh();

        createWebClient(context).post()
            .uri("/book/add")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .syncBody("{\"title\": \"title\", \"isbn\": \"10-isbn\", \"authors\": [\"1\", \"2\"], \"genres\": [\"10\", \"20\"]}")
            .exchange()
            .expectStatus().is2xxSuccessful();

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
    void updateAssertion() {
        LibraryService libraryService = mock(LibraryService.class);

        when(libraryService.getBookById(any())).thenReturn(Mono.just(new Book("101", "Nice")));
        when(libraryService.fillGenres(any(), any())).thenReturn(Mono.empty());
        when(libraryService.fillAuthors(any(), any())).thenReturn(Mono.empty());

        AnnotationConfigApplicationContext context = createApplicationContext();
        context.registerBean(LibraryService.class, () -> libraryService);
        context.refresh();

        createWebClient(context).post()
                .uri("/book/update/10")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .syncBody("{}")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().jsonPath("$.success").isEqualTo(false);
    }

    @Test
    @DisplayName("For deletion called right service method")
    void deletion() {
        LibraryService libraryService = mock(LibraryService.class);

        Book mockBook = new Book("", "");
        when(libraryService.delete(any())).thenReturn(Mono.empty());
        when(libraryService.getBookById(any())).thenReturn(Mono.just(mockBook));

        AnnotationConfigApplicationContext context = createApplicationContext();
        context.registerBean(LibraryService.class, () -> libraryService);
        context.refresh();

        createWebClient(context).post()
                .uri("/book/delete/10")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.success").isEqualTo(true);

        verify(libraryService, times(1)).delete(same(mockBook));
    }
}
