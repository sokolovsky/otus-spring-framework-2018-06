package ru.otus.spring.sokolovsky.hw11.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.sokolovsky.hw11.web.CommunityHandlers;
import ru.otus.spring.sokolovsky.hw11.web.HandbookHandlers;
import ru.otus.spring.sokolovsky.hw11.web.LibraryHandlers;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> libraryRouter(LibraryHandlers libraryHandlers) {
        return route(GET("/book/list"), libraryHandlers::bookList)
            .andRoute(GET("/book/get/{id}"), libraryHandlers::bookShow)
            .andRoute(POST("/book/add"), libraryHandlers::bookRegister)
            .andRoute(POST("/book/update/{id}"), libraryHandlers::bookUpdate)
            .andRoute(POST("/book/delete/{id}"), libraryHandlers::bookDelete)
            .andRoute(POST("/authors/{id}"), libraryHandlers::authorList)
            .andRoute(POST("/genre/{id}"), libraryHandlers::genreList);
    }

    @Bean
    public RouterFunction<ServerResponse> handbookRouter(HandbookHandlers handbookHandlers) {
        return route(GET("/genre/list"), handbookHandlers::genreList)
            .andRoute(GET("/author/list"), handbookHandlers::authorList);

    }

    @Bean
    public RouterFunction<ServerResponse> communityRouter(CommunityHandlers communityHandlers) {
        return route(POST("/comment/book/add/{bookId}"), communityHandlers::addBookComment)
            .andRoute(GET("/comment/book/get/{bookId}"), communityHandlers::getBookComments);
    }

    @Bean
    public RouterFunction<ServerResponse> resources() {
        return RouterFunctions.resources("/**", new ClassPathResource("public/"));
    }
}
