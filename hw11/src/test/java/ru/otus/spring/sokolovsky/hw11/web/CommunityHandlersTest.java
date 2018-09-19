package ru.otus.spring.sokolovsky.hw11.web;

import org.junit.jupiter.api.BeforeEach;
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
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.configuration.RouterConfiguration;
import ru.otus.spring.sokolovsky.hw11.domain.Book;
import ru.otus.spring.sokolovsky.hw11.domain.Comment;
import ru.otus.spring.sokolovsky.hw11.services.BookCommunityService;
import ru.otus.spring.sokolovsky.hw11.services.LibraryService;
import ru.otus.spring.sokolovsky.hw11.services.StatisticService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"/test-application.properties"})
class CommunityHandlersTest {

    private LibraryService libraryService;
    private BookCommunityService communityService;


    private WebTestClient createWebClient(ApplicationContext context) {
        return WebTestClient
                .bindToWebHandler(
                        RouterFunctions.toWebHandler((RouterFunction<?>) context.getBean("communityRouter"))
                ).build();
    }

    private AnnotationConfigApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean(LibraryHandlers.class);
        context.registerBean(HandbookHandlers.class);
        context.registerBean(CommunityHandlers.class);
        context.registerBean(StatisticService.class, () -> mock(StatisticService.class));
        context.register(RouterConfiguration.class);
        context.registerBean(LibraryService.class, () -> libraryService);
        context.registerBean(BookCommunityService.class, () -> communityService);
        context.refresh();
        return context;
    }

    @BeforeEach
    void before() {
        libraryService = mock(LibraryService.class);
        communityService = mock(BookCommunityService.class);
    }

    @Test
    @DisplayName("Comment into book has been added")
    void addCommentToBook() {
        Book book = new Book("12", "Title of book.");
        when(libraryService.getBookById("20")).thenReturn(Mono.just(book));
        when(communityService.registerBookComment(any(), any()))
                .thenReturn(Mono.just(book.addComment("Some message from the best book")));

        createWebClient(createApplicationContext()).post()
            .uri("/comment/book/add/20")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .syncBody("{\"text\": \"Some message from the best book\"}")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody().jsonPath("$.success").isEqualTo(true);

        //noinspection UnassignedFluxMonoInstance
        verify(communityService).registerBookComment(same(book), eq("Some message from the best book"));
    }

    @Test
    @DisplayName("Getting comments about book")
    void getComments() {
        Book mockBook = new Book("", "");
        Comment comment = mockBook.addComment("Some comment for testing");
        when(libraryService.getBookById("20")).thenReturn(Mono.just(mockBook));
        when(communityService.registerBookComment(any(), any())).thenReturn(Mono.just(comment));

        createWebClient(createApplicationContext()).get()
            .uri("/comment/book/get/20")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody().jsonPath("$[0].text").isEqualTo("Some comment for testing");
    }
}
