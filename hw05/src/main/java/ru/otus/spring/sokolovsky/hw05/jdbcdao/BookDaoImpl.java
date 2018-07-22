package ru.otus.spring.sokolovsky.hw05.jdbcdao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw05.domain.Author;
import ru.otus.spring.sokolovsky.hw05.domain.Book;
import ru.otus.spring.sokolovsky.hw05.domain.BookDao;
import ru.otus.spring.sokolovsky.hw05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class BookDaoImpl extends BaseDao implements BookDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public BookDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Book> getList(SqlSelectBuilder sqlBuilder, Map<String, Object> filter) {
        Objects.requireNonNull(sqlBuilder);
        List<Book> result = new ArrayList<>();
        jdbcTemplate.query(
                sqlBuilder
                        .useAlias("b")
                        .select("b.id b_id, b.title b_title, b.ISBN b_ISBN, a.id a_id, a.name a_name, g.id g_id, g.title g_title")
                        .join("left join `books_genres` bg on (bg.bookId=b.id) left join `genres` g on (g.id=bg.genreId)")
                        .join("left join `books_authors` ba on (ba.bookId=b.id) left join `authors` a on (a.id=ba.authorId)")
                        .useFilterFields(filter.keySet())
                        .toString(),
                filter,
                (resultSet) -> {
                    Map<Long, Book> bookMap = new HashMap<>();
                    Map<Long, Genre> genreMap = new HashMap<>();
                    Map<Long, Author> authorMap = new HashMap<>();

                    RowMapper bookRowMapper = new RowMapper(new PrefixCutTranslator("b_"));
                    GenreDaoImpl.RowMapper genreRowMapper = new GenreDaoImpl.RowMapper(new PrefixCutTranslator("g_"));
                    AuthorDaoImpl.RowMapper authorRowMapper = new AuthorDaoImpl.RowMapper(new PrefixCutTranslator("a_"));

                    int i = 0;
                    while (resultSet.next()) {
                        long id = resultSet.getLong("b_id");
                        if (!bookMap.containsKey(id)) {
                            Book entity = bookRowMapper.mapRow(resultSet, i);
                            bookMap.put(id, entity);
                            result.add(entity);
                        }

                        long genreId = resultSet.getLong("g_id");
                        Book book = bookMap.get(id);

                        if (!genreMap.containsKey(genreId)) {
                            Genre genre = genreRowMapper.mapRow(resultSet, i);
                            genreMap.put(genreId, genre);
                            book.addGenre(genre);
                        }

                        long authorId = resultSet.getLong("a_id");
                        if (!authorMap.containsKey(authorId)) {
                            Author author = authorRowMapper.mapRow(resultSet, i);
                            authorMap.put(authorId, author);
                            book.addAuthor(author);
                        }
                        i++;
                    }
                }
        );

        return result;
    }

    private List<Book> getList(Map<String, Object> filter) {
        return getList(createSelectBuilder(), filter);
    }

    private Book getOne(Map<String, Object> filter) {
        List<Book> list = getList(createSelectBuilder().limit(1), filter);
        return list.size() > 0 ? list.get(0) : null;
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

    @Override
    String getTableName() {
        return "books";
    }

    static class RowMapper extends BaseDao.RowMapper {

        RowMapper(ColumnNameTranslator columnNameTranslator) {
            super(columnNameTranslator);
        }

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book entity = new Book();
            entity.setId(rs.getLong("id"));
            entity.setISBN(rs.getString("ISBN"));
            entity.setTitle(rs.getString("title"));
            return entity;
        }
    }
}
