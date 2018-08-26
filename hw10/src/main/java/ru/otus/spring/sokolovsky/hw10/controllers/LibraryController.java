package ru.otus.spring.sokolovsky.hw10.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.sokolovsky.hw09.domain.Author;
import ru.otus.spring.sokolovsky.hw09.domain.Book;
import ru.otus.spring.sokolovsky.hw09.domain.BookValidator;
import ru.otus.spring.sokolovsky.hw09.domain.Genre;
import ru.otus.spring.sokolovsky.hw09.services.BookCommunityService;
import ru.otus.spring.sokolovsky.hw09.services.LibraryService;
import ru.otus.spring.sokolovsky.hw09.services.StatisticService;

import java.util.List;
import java.util.Objects;

@Controller
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

    @GetMapping("/books/{id}/edit")
    public String bookEdit(@PathVariable String id, Model model) {
        Book book = libraryService.getBookByIsbn(id);
        if (Objects.isNull(book)) {
            book = new Book("", "");
        }
        model.addAttribute("id", id);
        model.addAttribute("book", book);
        model.addAttribute("genres", libraryService.getGenres());
        model.addAttribute("authors", libraryService.getAuthors());
        return "books/form";
    }

    @PostMapping(value = "/books/{id}/save")
    public String bookSave(
            @PathVariable String id,
            @RequestParam String isbn,
            @RequestParam String title,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) List<String> authors,
            Model model) {
        Book book;
        if (id.equals("0")) {
            book = libraryService.registerBook(isbn, title);
        } else {
            book = libraryService.getBookByIsbn(id);
            if (Objects.isNull(book)) {
                throw new BadRequestException();
            }
            book.setIsbn(isbn);
            book.setTitle(title);
        }

        book.getGenres().clear();
        if (Objects.nonNull(genres)) {
            genres.forEach(g -> {
                Genre genre = libraryService.getGenreById(g);
                book.addGenre(genre);
            });
        }

        book.getAuthors().clear();
        if (Objects.nonNull(authors)) {
            authors.forEach(a -> {
                Author author = libraryService.getAuthorById(a);
                book.addAuthor(author);
            });
        }

        BookValidator validator = new BookValidator();
        Errors result = validator.validateWithResult(book);

        if (!result.hasErrors()) {
            libraryService.save(book);
            return "redirect:/books/" + book.getIsbn();
        } else {
            model.addAttribute("book", book);
            model.addAttribute("id", id);
            model.addAttribute("genres", libraryService.getGenres());
            model.addAttribute("authors", libraryService.getAuthors());
            model.addAttribute("result", result);
            return "books/form";
        }
    }

    @GetMapping("/books/{id}/delete")
    public String bookDelete(@PathVariable String id) {
        Book book = libraryService.getBookByIsbn(id);
        if (Objects.isNull(book)) {
            throw new BadRequestException();
        }
        libraryService.delete(book);
        return "redirect:/";
    }

    @PostMapping("/books/{id}/comment/add")
    public String addCommentToBook(@PathVariable String id, @RequestParam(required = false) String text) {
        Book book = libraryService.getBookByIsbn(id);
        if (Objects.isNull(book)) {
            throw new BadRequestException();
        }
        if (Objects.nonNull(text) && !text.trim().equals("")) {
            communityService.registerBookComment(book, text);
        }
        return "redirect:/books/" + id;
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
