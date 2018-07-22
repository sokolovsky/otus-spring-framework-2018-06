package ru.otus.spring.sokolovsky.hw05.dao;

import org.h2.result.Row;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
public class GenreDaoImpl extends BaseDao implements GenreDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public GenreDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getById(int id) {
        return jdbcTemplate.queryForObject(
                createSelectBuilder().useFilterFields("id").toString(),
                new HashMap<>() {{
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
    String getTableName() {
        return "genres";
    }

    static class RowMapper extends BaseDao.RowMapper {

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
