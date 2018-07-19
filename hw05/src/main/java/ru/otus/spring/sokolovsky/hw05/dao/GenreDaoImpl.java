package ru.otus.spring.sokolovsky.hw05.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw05.domain.Genre;

import java.util.List;

@Service
public class GenreDaoImpl implements GenreDao {

    private JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getById(int id) {
        return jdbcTemplate.queryForObject(
                "select * from `genres` where id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Genre.class)
        );
    }

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query("select * from `genres`", new BeanPropertyRowMapper<>(Genre.class));
    }
}
