package ru.otus.spring.sokolovsky.hw05.jdbcdao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw05.domain.Genre;
import ru.otus.spring.sokolovsky.hw05.domain.GenreDao;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class GenreDaoImpl extends BaseDao implements GenreDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public GenreDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getById(long id) {
        return jdbcTemplate.queryForObject(
                createSelectBuilder().useFilterFields("id").limit(1).toString(),
                new HashMap<String, Object>() {{
                    put("id", id);
                }},
                new BeanPropertyRowMapper<>(Genre.class)
        );
    }

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query(createSelectBuilder().toString(), new BeanPropertyRowMapper<>(Genre.class));
    }

    @Override
    public Genre findByTitle(String s) {
        try {
            return jdbcTemplate.queryForObject(
                    createSelectBuilder().addWhere("title like :title").limit(1).toString(),
                    new HashMap<>() {{
                        put("title", "%" + s + "%");
                    }},
                    new RowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void insert(Genre entity) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.getJdbcTemplate().update(
                (Connection c) -> {
                    PreparedStatement statement = c.prepareStatement(
                            "insert into " + getTableName() + " (title) values (?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    statement.setString(1, entity.getTitle());
                    return statement;
                },
                holder
        );

        entity.setId(Objects.requireNonNull(holder.getKey()).longValue());
    }

    @Override
    String getTableName() {
        return "genres";
    }

    static class RowMapper extends BaseDao.RowMapper<Genre> {

        RowMapper() {
            super();
        }

        RowMapper(ColumnNameTranslator columnNameTranslator) {
            super(columnNameTranslator);
        }

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre entity = new Genre();
            entity.setId(rs.getLong(getColumnId("id")));
            entity.setTitle(rs.getString(getColumnId("title")));
            return entity;
        }
    }
}
