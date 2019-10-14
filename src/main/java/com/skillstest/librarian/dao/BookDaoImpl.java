package com.skillstest.librarian.dao;

import com.skillstest.librarian.domain.model.Book;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class BookDaoImpl implements BookDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> getBooks() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery( Book.class );
        return entityManager.createQuery( criteria ).getResultList();
    }

    @Override
    public List<Book> getByTitle(String title) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery( Book.class );
        Root<Book> root = criteria.from(Book.class);
        criteria.where(builder.like(root.get("title"), title));
        List<Book> books = entityManager.createQuery(criteria).getResultList();
        return books;
    }

    @Override
    public List<Book> getByAuthorName( String name ) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery( Book.class );
        Root<Book> root = criteria.from(Book.class);
        Predicate filterByFirstName = builder.like(root.get("authors.author.firstName"), name);
        Predicate filterByLastName = builder.like(root.get("authors.author.lastName"), name);
        criteria.where(builder.or(filterByFirstName, filterByLastName));
        List<Book> books = entityManager.createQuery(criteria).getResultList();
        return books;
    }

    @Override
    public Book getById(Long id) {
        return entityManager.find( Book.class, id );
    }

    @Override
    public void update(Book book) {
        entityManager.unwrap(Session.class).saveOrUpdate(book);
    }

    @Override
    public void delete(Book book) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(book);
    }

    @Override
    public void create(Book book) {
        entityManager.persist(book);
    }
}
