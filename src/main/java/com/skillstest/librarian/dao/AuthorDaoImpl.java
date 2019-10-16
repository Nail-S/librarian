package com.skillstest.librarian.dao;

import com.skillstest.librarian.domain.model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuthorDaoImpl implements AuthorDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Author> getAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteria = builder.createQuery( Author.class );
        Root<Author> root = criteria.from(Author.class);
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
//TODO Fix the attribute path problem
    @Override
    public List<Author> findByBookTitle(String title) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        Root<Author> root = criteria.from(Author.class);
        criteria.where(builder.like(root.get("books.book.title"), title));
        List<Author> authors = entityManager.createQuery(criteria).getResultList();
        return authors;
    }

    @Override
    public Optional<Author> getById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.byId(Author.class).loadOptional(id);
    }

    @Override
    public Author update(Author author) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(author);
        return session.byId(Author.class).load(author.getId());
    }

    @Override
    public void delete(Author author) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(author);
    }

    @Override
    public Author create(Author author) {
        Session session = entityManager.unwrap(Session.class);
        Long id = (Long) session.save(author);
        return session.byId(Author.class).load(id);
    }

    @Override
    public List<Author> createAll(List<Author> authors) {
        SessionFactory factory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
        StatelessSession session = factory.openStatelessSession();
        List<Author> saved = authors.stream()
                .map(b -> session.insert(b))
                .map(id -> (Author) session.get(Author.class, id))
                .collect(Collectors.toList());
        return saved;
    }
}
