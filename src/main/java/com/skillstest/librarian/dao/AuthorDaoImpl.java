package com.skillstest.librarian.dao;

import com.skillstest.librarian.domain.model.Author;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Author> getAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteria = builder.createQuery( Author.class );
        return entityManager.createQuery( criteria ).getResultList();
    }

    @Override
    public List<Author> findByName(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteria = builder.createQuery( Author.class );
        Root<Author> root = criteria.from(Author.class);
        Predicate filterByFirstName = builder.like(root.get("firstName"), name);
        Predicate filterByLastName = builder.like(root.get("lastName"), name);
        criteria.where(builder.or(filterByFirstName, filterByLastName));
        List<Author> authors = entityManager.createQuery(criteria).getResultList();
        return authors;
    }

    @Override
    public List<Author> findByBookTitle(String title) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteria = builder.createQuery( Author.class );
        Root<Author> root = criteria.from(Author.class);
        criteria.where(builder.like(root.get("books.book.title"), title));
        List<Author> authors = entityManager.createQuery(criteria).getResultList();
        return authors;
    }

    @Override
    public Author getById(Long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public void update(Author author) {
        entityManager.unwrap(Session.class).saveOrUpdate(author);
    }

    @Override
    public void delete(Author author) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(author);
    }

    @Override
    public void create(Author author) {
        entityManager.persist(author);
    }
}
