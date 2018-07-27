package ru.otus.spring.sokolovsky.hw06.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.sokolovsky.hw06.domain.Author;
import ru.otus.spring.sokolovsky.hw06.domain.Book;
import ru.otus.spring.sokolovsky.hw06.domain.BookDao;
import ru.otus.spring.sokolovsky.hw06.domain.Genre;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookRepository extends BaseJpaRepository<Book> implements BookDao {
    @Override
    public Book getById(int id) {
        TypedQuery<Book> query = getEm()
                .createQuery("select b from Book b where b.id = :id", Book.class)
                .setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Book getByISBN(String ISBN) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public List<Book> getByCategories(Author author, Genre genre) {
        return null;
    }

    @Override
    public void save(Book entity) {
    }

    @Override
    public void delete(Book entity) {
    }
}
