package ru.otus.spring.sokolovsky.hw05.jdbcdao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw05.domain.Author;
import ru.otus.spring.sokolovsky.hw05.domain.AuthorDao;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class AuthorDaoImpl extends BaseDao implements AuthorDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getById(int id) {
        return jdbcTemplate.queryForObject(
                createSelectBuilder().useFilterFields("id").toString(),
                new HashMap<>() {{
                    put("id", id);
                }},
                new RowMapper()
        );
    }

    @Override
    public List<Author> getAll() {
        return jdbcTemplate.query(createSelectBuilder().toString(), new BeanPropertyRowMapper<>(Author.class));
    }

    @Override
    public Author getByName(String s) {
        try {
            return jdbcTemplate.queryForObject(
                    createSelectBuilder().addWhere("name like :name").toString(),
                    new HashMap<>() {{
                        put("name", "%" + s + "%");
                    }},
                    new RowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void insert(Author entity) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.getJdbcTemplate().update(
                (Connection c) -> {
                    PreparedStatement statement = c.prepareStatement(
                            "insert into " + getTableName() + " (name) values (?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    statement.setString(1, entity.getName());
                    return statement;
                },
                holder
        );

        entity.setId(Objects.requireNonNull(holder.getKey()).longValue());
    }

    @Override
    String getTableName() {
        return "authors";
    }

    static class RowMapper extends BaseDao.RowMapper<Author> {
        RowMapper() {
            super();
        }
        RowMapper(ColumnNameTranslator columnNameTranslator) {
            super(columnNameTranslator);
        }
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author entity = new Author();
            entity.setId(rs.getLong(getColumnId("id")));
            entity.setName(rs.getString(getColumnId("name")));
            return entity;
        }
    }
}
