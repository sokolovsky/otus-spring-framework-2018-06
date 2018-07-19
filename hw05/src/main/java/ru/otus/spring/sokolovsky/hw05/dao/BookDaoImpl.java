package ru.otus.spring.sokolovsky.hw05.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw05.domain.Author;
import ru.otus.spring.sokolovsky.hw05.domain.Book;
import ru.otus.spring.sokolovsky.hw05.domain.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookDaoImpl implements BookDao{

    private JdbcTemplate jdbcTemplate;

    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Book> getList(Map<String, Object> filter) {
        SqlFilter sqlFilter = new SqlFilter(filter);
        return jdbcTemplate.query(
                "select * from `books`" + sqlFilter.getSql(),
                sqlFilter.getValues(),
                new BeanPropertyRowMapper<>(Book.class)
            );
    }

    private Book getOne(Map<String, Object> filter) {
        SqlFilter sqlFilter = new SqlFilter(filter);
        return jdbcTemplate.queryForObject(
                "select * from `books`" + sqlFilter.getSql(),
                sqlFilter.getValues(),
                new BeanPropertyRowMapper<>(Book.class)
        );
    }

    @Override
    public Book getById(int id) {
        return getOne(new HashMap<>() {{
            put("id", id);
        }});
    }

    @Override
    public Book getByISBN(String ISBN) {
        return getOne(new HashMap<>() {{
            put("ISBN", ISBN);
        }});
    }

    @Override
    public List<Book> getByAuthor(int authorId) {
        return null;
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        return null;
    }

    @Override
    public List<Book> getByGenre(int genreId) {
        return null;
    }

    @Override
    public List<Book> getByGenre(Genre genre) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return getList(Map.of());
    }
}
