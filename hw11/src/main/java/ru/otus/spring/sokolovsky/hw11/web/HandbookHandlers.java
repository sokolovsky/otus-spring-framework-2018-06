package ru.otus.spring.sokolovsky.hw11.web;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.domain.Author;
import ru.otus.spring.sokolovsky.hw11.domain.Genre;
import ru.otus.spring.sokolovsky.hw11.services.LibraryService;
import ru.otus.spring.sokolovsky.hw11.services.StatisticService;

import java.util.*;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Service
public class HandbookHandlers {

    private LibraryService libraryService;
    private StatisticService statisticService;

    public HandbookHandlers(LibraryService libraryService, StatisticService statisticService) {
        this.libraryService = libraryService;
        this.statisticService = statisticService;
    }

    @SuppressWarnings("ConstantConditions")
    public Mono<ServerResponse> genreList(ServerRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("genres", new ArrayList<>());

        Flux<Genre> genreFlux = libraryService.getGenres().doOnEach(s -> {
            if (Objects.isNull(s.get())) {
                return;
            }
            List authors = (List) map.get("genres");
            //noinspection unchecked
            authors.add(s.get());
        });
        Mono<StatisticService.EntityStatistic> statisticMono = statisticService.getGenreToBookStatistic().doOnEach(s -> {
            if (Objects.isNull(s.get())) {
                // side effect callback
                return;
            }
            map.put("statistic", s.get().getMap());
        });
        return Mono.when(genreFlux, statisticMono).then(ServerResponse.ok().body(fromObject(map)));
    }

    @SuppressWarnings("ConstantConditions")
    public Mono<ServerResponse> authorList(ServerRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("authors", new ArrayList<>());
        Flux<Author> genreFlux = libraryService.getAuthors().doOnEach(s -> {
            if (Objects.isNull(s.get())) {
                return;
            }
            List authors = (List) map.get("authors");
            //noinspection unchecked
            authors.add(s.get());
        });
        Mono<StatisticService.EntityStatistic> statisticMono = statisticService.getAuthorsToBookStatistic().doOnEach(s -> {
            if (Objects.isNull(s.get())) {
                // side effect callback
                return;
            }
            map.put("statistic", s.get().getMap());
        });
        return Mono.when(genreFlux, statisticMono).then(ServerResponse.ok().body(fromObject(map)));
    }
}
