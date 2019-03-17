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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.configuration.RouterConfiguration;
import ru.otus.spring.sokolovsky.hw11.domain.Author;
import ru.otus.spring.sokolovsky.hw11.domain.Genre;
import ru.otus.spring.sokolovsky.hw11.services.BookCommunityService;
import ru.otus.spring.sokolovsky.hw11.services.LibraryService;
import ru.otus.spring.sokolovsky.hw11.services.StatisticService;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"/test-application.properties"})
public class HandbookHandlersTest  {


    private LibraryService libraryService;
    private StatisticService statisticService;

    private AnnotationConfigApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean(LibraryHandlers.class);
        context.registerBean(HandbookHandlers.class);
        context.registerBean(CommunityHandlers.class);
        context.registerBean(StatisticService.class, () -> statisticService);
        context.registerBean(LibraryService.class, () -> libraryService);
        context.registerBean(BookCommunityService.class, () -> mock(BookCommunityService.class));

        context.register(RouterConfiguration.class);
        context.refresh();
        return context;
    }

    private WebTestClient createWebClient(ApplicationContext context) {
        return WebTestClient
                .bindToWebHandler(
                        RouterFunctions.toWebHandler((RouterFunction<?>) context.getBean("handbookRouter"))
                ).build();
    }

    @BeforeEach
    void before() {
        libraryService = mock(LibraryService.class);
        statisticService = mock(StatisticService.class);
    }

    @Test
    @DisplayName("Getting list of genres")
    void genres() {

        Genre one = new Genre("One");
        one.setId("One");

        Genre two = new Genre("Two");
        two.setId("Two");

        when(libraryService.getGenres())
                .thenReturn(Flux.just(
                        one,
                        two
                ));
        when(statisticService.getGenreToBookStatistic())
                .thenReturn(Mono.just(new StatisticService.EntityStatistic(new HashMap<>(){{
                    put(one.getId(), 10);
                    put(two.getId(), 15);
                }})));

        createWebClient(createApplicationContext()).get()
                .uri("/genre/list")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.genres[0].id").isEqualTo("One")
                .jsonPath("$.genres[0].title").isEqualTo("One")
                .jsonPath("$.statistic.Two").isEqualTo(15)
                ;
    }

    @Test
    @DisplayName("Getting list of authors")
    void authors() {

        Author one = new Author("One");
        one.setId("One");

        Author two = new Author("Two");
        two.setId("Two");

        when(libraryService.getAuthors())
                .thenReturn(Flux.just(
                        one,
                        two
                ));
        when(statisticService.getAuthorsToBookStatistic())
                .thenReturn(Mono.just(new StatisticService.EntityStatistic(new HashMap<>(){{
                    put(one.getId(), 10);
                    put(two.getId(), 25);
                }})));

        createWebClient(createApplicationContext()).get()
                .uri("/author/list")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.authors[0].id").isEqualTo("One")
                .jsonPath("$.authors[0].name").isEqualTo("One")
                .jsonPath("$.statistic.Two").isEqualTo(25)
                ;
    }
}
