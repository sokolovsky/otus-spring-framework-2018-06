package ru.otus.spring.sokolovsky.hw05.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw05.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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
                new BeanPropertyRowMapper<>(Author.class)
        );
    }

    @Override
    public List<Author> getAll() {
        return jdbcTemplate.query(createSelectBuilder().toString(), new BeanPropertyRowMapper<>(Author.class));
    }

    @Override
    String getTableName() {
        return "authors";
    }

    static class RowMapper extends BaseDao.RowMapper {
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
