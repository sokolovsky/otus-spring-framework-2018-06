package ru.otus.spring.sokolovsky.hw11.web;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.sokolovsky.hw11.domain.Book;
import ru.otus.spring.sokolovsky.hw11.services.BookCommunityService;
import ru.otus.spring.sokolovsky.hw11.services.LibraryService;
import ru.otus.spring.sokolovsky.hw11.services.NotExistException;

import java.util.Map;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Service
public class CommunityHandlers {

    private final LibraryService libraryService;
    private final BookCommunityService communityService;

    public CommunityHandlers(LibraryService libraryService, BookCommunityService communityService) {
        this.libraryService = libraryService;
        this.communityService = communityService;
    }

    public Mono<ServerResponse> addBookComment(ServerRequest request) {
        String bookId = request.pathVariable("bookId");
        if (bookId.trim().equals("")) {
            return ServerResponse.badRequest().build();
        }

        return Mono.just(bookId)
                .flatMap(bId -> {
                    Mono<Book> bookMono = libraryService.getBookById(bId);
                    Mono<Map> bodyMono = request.bodyToMono(Map.class);

                    return Mono.zip(bookMono, bodyMono, (book, body) -> {
                        return communityService.registerBookComment(book, (String) body.get("text")).block();
                    });
                }).flatMap(commentMono -> ServerResponse.ok().body(fromObject(ActionResult.ok())))
                .onErrorResume(NotExistException.class, e -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getBookComments(ServerRequest request) {
        String bookId = request.pathVariable("bookId");
        if (bookId.trim().equals("")) {
            return ServerResponse.badRequest().build();
        }

        return Mono.just(bookId)
            .flatMap(libraryService::getBookById)
            .map(Book::getComments)
            .flatMap(comments -> ServerResponse.ok().body(fromObject(comments)));
    }
}
