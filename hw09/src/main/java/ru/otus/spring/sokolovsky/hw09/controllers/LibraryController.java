package ru.otus.spring.sokolovsky.hw09.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.spring.sokolovsky.hw09.domain.Author;
import ru.otus.spring.sokolovsky.hw09.domain.Book;
import ru.otus.spring.sokolovsky.hw09.domain.Genre;
import ru.otus.spring.sokolovsky.hw09.services.LibraryService;
import ru.otus.spring.sokolovsky.hw09.services.StatisticService;

import java.util.List;
import java.util.Objects;

@Controller
public class LibraryController {

    private final LibraryService libraryService;

    private final StatisticService statisticService;

    @Autowired
    public LibraryController(LibraryService libraryService, StatisticService statisticService) {
        this.libraryService = libraryService;
        this.statisticService = statisticService;
    }

    @GetMapping("/")
    public String books(Model model) {
        List<Book> list = libraryService.getList(null, null);
        model.addAttribute("books", list);
        return "books/list";
    }

    @GetMapping("/books/{id}")
    public String bookShow(@PathVariable String id, Model model) {
        Book book = libraryService.getBookByIsbn(id);
        if (Objects.isNull(book)) {
            throw new ResourceNotFoundException();
        }
        model.addAttribute("book", book);
        return "books/detail";
    }

    @GetMapping("/authors/")
    public String authors(Model model) {
        model.addAttribute("authors", libraryService.getAuthors());
        model.addAttribute("statistic", statisticService.getAuthorsToBookStatistic());
        return "authors";
    }

    @GetMapping("/authors/{authorId}")
    public String bookByAuthor(@PathVariable String authorId, Model model) {
        Author author = libraryService.getAuthorById(authorId);
        if (Objects.isNull(author)) {
            throw new ResourceNotFoundException();
        }
        List<Book> list = libraryService.getList(authorId, null);
        model.addAttribute("books", list);
        return "books/list";
    }

    @GetMapping("/genres/")
    public String genres(Model model) {
        model.addAttribute("genres", libraryService.getGenres());
        model.addAttribute("statistic", statisticService.getGenreToBookStatistic());
        return "genres";
    }

    @GetMapping("/genres/{genreId}")
    public String bookByGenre(@PathVariable String genreId, Model model) {
        Genre genre = libraryService.getGenreById(genreId);
        if (Objects.isNull(genre)) {
            throw new ResourceNotFoundException();
        }
        List<Book> list = libraryService.getList(null, genreId);
        model.addAttribute("books", list);
        return "books/list";
    }
}
