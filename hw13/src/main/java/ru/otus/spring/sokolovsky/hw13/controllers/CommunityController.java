package ru.otus.spring.sokolovsky.hw13.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.sokolovsky.hw13.domain.Book;
import ru.otus.spring.sokolovsky.hw13.domain.Comment;
import ru.otus.spring.sokolovsky.hw13.services.AccessTestService;
import ru.otus.spring.sokolovsky.hw13.services.BookCommunityService;
import ru.otus.spring.sokolovsky.hw13.services.LibraryService;
import ru.otus.spring.sokolovsky.hw13.services.NotExistException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("${api.base.path:}")
public class CommunityController {

    private LibraryService libraryService;
    private BookCommunityService communityService;

    @Autowired
    AccessTestService accessTestService;

    public CommunityController(LibraryService libraryService, BookCommunityService communityService) {
        this.libraryService = libraryService;
        this.communityService = communityService;
    }

    @PostMapping("/comment/book/add/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public ActionResult addCommentToBook(@PathVariable String bookId, @RequestBody Map<String, Object> body) {
        Book book;
        try {
            book = libraryService.getBookById(bookId);
        } catch (NotExistException e) {
            throw new BadRequestException("Book is missed.");
        }

        String text = (String) body.getOrDefault("text", null);
        if (Objects.isNull(book)) {
            throw new BadRequestException("Book is not exists");
        }
        ActionResult res;
        if (Objects.nonNull(text) && !text.trim().equals("")) {
            communityService.registerBookComment(book, text);
            res = ActionResult.ok();
        } else {
            res = ActionResult.error("Message is empty.");
        }
        return res;
    }

    @GetMapping("/comment/book/get/{bookId}")
    public List<Comment> getBookComments(@PathVariable String bookId) {
        Book book;
        try {
            book = libraryService.getBookById(bookId);
        } catch (NotExistException e) {
            throw new BadRequestException("Book is missed.");
        }
        return book.getComments();
    }

    @GetMapping("/comment/book/canLeaveComment/{bookId}")
    public ActionResult canLeaveBookComment(@PathVariable String bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean res = accessTestService.canLeaveBookComment(authentication, libraryService.getBookById(bookId));
        return ActionResult.ok().data(new HashMap<>() {
            {
                put("result", res);
            }
        });
    }
}
