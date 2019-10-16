package com.skillstest.librarian.dao;

import com.skillstest.librarian.domain.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookDaoImpl implements BookDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> getBooks() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = builder.createQuery( Book.class );
        Root<Book> root = criteria.from(Book.class);
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
    //TODO Fix the attribute path problem
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
    public Optional<Book> getById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.byId(Book.class).loadOptional(id);
    }

    @Override
    public Book update(Book book) {
        Session session = entityManager.unwrap(Session.class);
        return (Book) session.merge(book);
    }

    @Override
    public void delete(Book book) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(book);
    }

    @Override
    public Book create(Book book) {
        Session session = entityManager.unwrap(Session.class);
        Long id = (Long) session.save(book);
        return  session.byId(Book.class).load(id);
    }

    @Override
    public List<Book> createAll(List<Book> books) {
        SessionFactory factory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
        StatelessSession session = factory.openStatelessSession();
        List<Book> saved = books.stream()
            .map(b -> session.insert(b))
            .map(id -> (Book) session.get(Book.class, id))
            .collect(Collectors.toList());
        return saved;
    }
}
