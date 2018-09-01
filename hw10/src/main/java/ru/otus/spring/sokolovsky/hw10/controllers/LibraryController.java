package ru.otus.spring.sokolovsky.hw10.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.sokolovsky.hw10.domain.Author;
import ru.otus.spring.sokolovsky.hw10.domain.Book;
import ru.otus.spring.sokolovsky.hw10.domain.BookValidator;
import ru.otus.spring.sokolovsky.hw10.domain.Genre;
import ru.otus.spring.sokolovsky.hw10.services.BookCommunityService;
import ru.otus.spring.sokolovsky.hw10.services.LibraryService;
import ru.otus.spring.sokolovsky.hw10.services.NotExistException;
import ru.otus.spring.sokolovsky.hw10.services.StatisticService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class LibraryController {

    private final LibraryService libraryService;
    private final BookCommunityService communityService;
    private final StatisticService statisticService;

    @Autowired
    public LibraryController(LibraryService libraryService, BookCommunityService communityService, StatisticService statisticService) {
        this.libraryService = libraryService;
        this.communityService = communityService;
        this.statisticService = statisticService;
    }

    @GetMapping("/book/list/")
    public List<Book> books() {
        return libraryService.getList(null, null);
    }

    @GetMapping("/book/get/{id}")
    public Book bookShow(@PathVariable String id) {
        try {
            return libraryService.getBookById(id);
        } catch (NotExistException e) {
            throw new BadRequestException(String.format("Book with id %s not exists", id));
        }
    }

    @PostMapping(value = "/books/add/")
    public ActionResult bookSave(
            @RequestParam String isbn,
            @RequestParam String title,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) List<String> authors
            ) {

        Book book = libraryService.registerBook(isbn, title);
        libraryService.fillGenres(book, genres);
        libraryService.fillAuthors(book, authors);
        libraryService.save(book);

        BookValidator validator = new BookValidator();
        Errors result = validator.validateWithResult(book);

        if (!result.hasErrors()) {
            libraryService.save(book);
            return ActionResult.ok("");
        } else {
            ActionResult error = ActionResult.error("Has some errors");
            Map<String, Object> errorMap = new HashMap<>();
            result.getAllErrors().forEach(e -> {
                errorMap.put(e.getObjectName(), e.hashCode());
            });
            error.setData(errorMap);
            return error;
        }
    }

    @PostMapping(value = "/books/update/{id}")
    public ActionResult bookUpdate(
            @PathVariable String id,
            @RequestParam String isbn,
            @RequestParam String title,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) List<String> authors) {

        Book book;
        try {
            book = libraryService.getBookById(id);
        } catch (NotExistException e) {
            throw new BadRequestException(String.format("Book with id %s not exists", id));
        }
        book.setIsbn(isbn);
        book.setTitle(title);

        book.getGenres().clear();
        libraryService.fillGenres(book, genres);

        book.getAuthors().clear();
        libraryService.fillAuthors(book, authors);

        BookValidator validator = new BookValidator();
        Errors result = validator.validateWithResult(book);

        if (!result.hasErrors()) {
            libraryService.save(book);
            return ActionResult.ok("");
        } else {
            ActionResult error = ActionResult.error("Has some errors");
            Map<String, Object> errorMap = new HashMap<>();
            result.getAllErrors().forEach(e -> {
                errorMap.put(e.getObjectName(), e.hashCode());
            });
            error.setData(errorMap);
            return error;
        }
    }

    @PostMapping("/books/delete/{id}")
    public ActionResult bookDelete(@PathVariable String id) {
        Book book;
        try {
            book = libraryService.getBookById(id);
        } catch (NotExistException e) {
            throw new BadRequestException(String.format("Book with id %s not exists", id));
        }
        libraryService.delete(book);
        return ActionResult.ok("");
    }

    @PostMapping("/books/{id}/comment/add")
    public String addCommentToBook(@PathVariable String id, @RequestParam(required = false) String text) {
        Book book = libraryService.getBookByIsbn(id);
        if (Objects.isNull(book)) {
            throw new BadRequestException("");
        }
        if (Objects.nonNull(text) && !text.trim().equals("")) {
            communityService.registerBookComment(book, text);
        }
        return "redirect:/books/" + id;
    }

    @GetMapping("/authors/")
    public Map<String, Object> authors() {
        Map<String, Object> map = new HashMap<>();
        map.put("authors", libraryService.getAuthors());
        map.put("statistic", statisticService.getAuthorsToBookStatistic());
        return map;
    }

    @GetMapping("/authors/{authorId}")
    public List<Book> bookByAuthor(@PathVariable String authorId) {
        Author author = libraryService.getAuthorById(authorId);
        if (Objects.isNull(author)) {
            throw new ResourceNotFoundException();
        }
        return libraryService.getList(authorId, null);
    }

    @GetMapping("/genres/")
    public Map<String, Object> genres() {
        Map<String, Object> map = new HashMap<>();
        map.put("genres", libraryService.getGenres());
        map.put("statistic", statisticService.getGenreToBookStatistic());
        return map;
    }

    @GetMapping("/genres/{genreId}")
    public List<Book> bookByGenre(@PathVariable String genreId) {
        Genre genre = libraryService.getGenreById(genreId);
        if (Objects.isNull(genre)) {
            throw new ResourceNotFoundException();
        }
        return libraryService.getList(null, genreId);
    }
}
