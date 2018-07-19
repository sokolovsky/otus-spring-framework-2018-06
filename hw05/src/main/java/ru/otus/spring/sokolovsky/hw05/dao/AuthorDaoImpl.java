package ru.otus.spring.sokolovsky.hw05.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw05.domain.Author;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorDaoImpl implements AuthorDao {

    private JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getById(int id) {
        return jdbcTemplate.queryForObject(
                "select * from `authors` where id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Author.class)
        );
    }

    @Override
    public List<Author> getAll() {
        return jdbcTemplate.query("select * from `authors`", new BeanPropertyRowMapper<>(Author.class));
    }
}
