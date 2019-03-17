package ru.otus.spring.sokolovsky.hw11.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.domain.Book;
import ru.otus.spring.sokolovsky.hw11.domain.BookValidator;
import ru.otus.spring.sokolovsky.hw11.services.LibraryService;
import ru.otus.spring.sokolovsky.hw11.services.NotExistException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Service
public class LibraryHandlers {

    private LibraryService libraryService;

    public LibraryHandlers(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public Mono<ServerResponse> bookList(ServerRequest request) {
        Predicate<String> notEmpty = s -> !s.equals("");
        Optional<String> genre = request.queryParam("genre").filter(notEmpty);
        Optional<String> author = request.queryParam("author").filter(notEmpty);

        return ServerResponse.ok().body(libraryService.getList(author.orElse(null), genre.orElse(null)), Book.class);
    }

    public Mono<ServerResponse> bookShow(ServerRequest request) {
        try {
            String id = request.pathVariable("id");
            return ServerResponse.ok().body(libraryService.getBookById(id), Book.class);
        } catch (NotExistException e) {
            return ServerResponse.notFound().build();
        }
    }

    public Mono<ServerResponse> bookRegister(ServerRequest request) {
        Mono<HashMap> data = request.bodyToMono(HashMap.class);
        BookValidator validator = new BookValidator();

        return data
            .flatMap(body -> {
                Book book = new Book((String) body.get("isbn"), (String) body.get("title"));
                return Mono
                    .when(
                        libraryService.fillGenres(book, (List<String>) body.get("genres")),
                        libraryService.fillAuthors(book, (List<String>) body.get("authors"))
                    ).then(Mono.just(book));
            })
            .handle((book, sink) -> {
                Errors result = validator.validateWithResult(book);
                if (!result.hasErrors()) {
                    sink.next(book);
                    return;
                }
                Map<String, Object> errorMap = result.getAllErrors().stream()
                    .collect(Collectors.toMap(ObjectError::getObjectName, ObjectError::hashCode, (v1, v2) -> v1));
                sink.error(new IllegalPostDataException(errorMap));
            })
            .flatMap(book -> libraryService.save((Book) book))
            .flatMap(book -> {
                return ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(fromObject(ActionResult.ok()));
            })
            .onErrorResume(IllegalPostDataException.class, e -> {
                ActionResult error = ActionResult.error("Has some errors");
                error.setData(e.getErrors());
                return ServerResponse
                        .badRequest()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(fromObject(error));
            });
    }

    public Mono<ServerResponse> bookUpdate(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Map> data = request.bodyToMono(HashMap.class);
        BookValidator validator = new BookValidator();

        return Mono.just(id)
            .flatMap(eid -> libraryService.getBookById(eid))
            .flatMap(book -> {
                return data
                        .handle((d, sink) -> {
                            libraryService.fillGenres(book, (List<String>) d.get("genres"));
                            libraryService.fillAuthors(book, (List<String>) d.get("authors"));
                        })
                        .then(Mono.just(book));
            })
            .handle((book, sink) -> {
                Errors result = validator.validateWithResult(book);
                if (!result.hasErrors()) {
                    sink.next(book);
                    return;
                }
                Map<String, Object> errorMap = result.getAllErrors().stream()
                        .collect(Collectors.toMap(ObjectError::getObjectName, ObjectError::hashCode, (v1, v2) -> v1));
                sink.error(new IllegalPostDataException(errorMap));
            })
            .flatMap(book -> libraryService.save((Book) book))
            .flatMap(book -> {
                return ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(fromObject(ActionResult.ok()));
            })
            .onErrorResume(IllegalPostDataException.class, e -> {
                ActionResult error = ActionResult.error("Has some errors");
                error.setData(e.getErrors());
                return ServerResponse
                        .badRequest()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(fromObject(error));
            })
            .onErrorResume(NotExistException.class, e -> {
                return ServerResponse.badRequest().body(fromObject(e.getMessage()));
            });
    }

    public Mono<ServerResponse> bookDelete(ServerRequest request) {
        String id = request.pathVariable("id");
        return Mono.just(id)
            .flatMap(eid -> libraryService.getBookById(eid))
            .flatMap(book -> {
                return Mono.when(libraryService.delete(book))
                        .then(ServerResponse.ok().body(fromObject(ActionResult.ok())));

            })
            .onErrorResume(NotExistException.class, e -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> authorList(ServerRequest request) {
        String id = request.pathVariable("id");
        return Mono.just(id)
                .flatMap(eid -> libraryService.getAuthorById(eid))
                .flatMap(author -> {
                    Flux<Book> list = libraryService.getList(id, null);
                    return ServerResponse.ok().body(fromPublisher(list, Book.class));
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> genreList(ServerRequest request) {
        String id = request.pathVariable("id");
        return Mono.just(id)
                .flatMap(eid -> libraryService.getGenreById(eid))
                .flatMap(genre -> {
                    Flux<Book> list = libraryService.getList(null, id);
                    return ServerResponse.ok().body(fromPublisher(list, Book.class));
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
