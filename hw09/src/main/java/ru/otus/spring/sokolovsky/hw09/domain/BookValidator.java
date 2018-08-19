package ru.otus.spring.sokolovsky.hw09.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.Objects;

public class BookValidator implements Validator  {
    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    public Errors validateWithResult(Book book) {
        MapBindingResult result = new MapBindingResult(new HashMap<>(), "book");
        validate(book, result);
        return result;
    }

    @Override
    public void validate(Object o, @NotNull Errors errors) {
        Book book = (Book) o;
        if (Objects.isNull(book.getTitle()) || book.getTitle().trim().equals("")) {
            errors.rejectValue("title", "genres.empty");
        }
        if (Objects.isNull(book.getIsbn()) || book.getIsbn().trim().equals("")) {
            errors.rejectValue("isbn", "genres.empty");
        }
        if (book.getGenres().size() == 0) {
            errors.rejectValue("genres", "genres.empty");
        }
        if (book.getAuthors().size() == 0) {
            errors.rejectValue("authors", "authors.empty");
        }
    }
}
